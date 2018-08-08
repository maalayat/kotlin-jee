package ec.solmedia.kotlin.jee.ejb

import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.persistence.criteria.*
import javax.persistence.metamodel.SingularAttribute

typealias PredicateBuilder<T> = (criteriaBuilder: CriteriaBuilder, root: Root<T>) -> Predicate
typealias QueryBuilder<T> = (criteriaBuilder: CriteriaBuilder, root: Root<T>, criteriaQuery: CriteriaQuery<T>) -> CriteriaQuery<T>
typealias UpdateBuilder<T> = (criteriaBuilder: CriteriaBuilder, root: Root<T>, criteriaUpdate: CriteriaUpdate<T>) -> CriteriaUpdate<T>

abstract class AbstractDao<T>(
        private val entityClass: Class<T>) {

    @PersistenceContext(name = "primary")
    protected lateinit var em: EntityManager

    fun persist(entity: T) = em.persist(entity)

    fun merge(entity: T) = em.merge(entity)

    fun remove(entity: T) = em.remove(entity)

    fun getById(id: Long) = em.find(entityClass, id)

    private fun createTypedQuery(queryBuilder: QueryBuilder<T>): TypedQuery<T> {
        val criteriaBuilder = em.criteriaBuilder
        val queryClass = criteriaBuilder.createQuery(entityClass)
        val root = queryClass.from(entityClass)
        val criteriaQuery = queryBuilder(criteriaBuilder, root, queryClass.select(root))

        return em.createQuery(criteriaQuery)
    }

    private fun createUpdateQuery(updateBuilder: UpdateBuilder<T>) {
        val criteriaBuilder = em.criteriaBuilder
        val updateClass = criteriaBuilder.createCriteriaUpdate(entityClass)
        val root = updateClass.from(entityClass)
        val criteriaUpdate = updateBuilder(criteriaBuilder, root, updateClass)
        em.createQuery(criteriaUpdate).executeUpdate()
    }

    private fun buildPredicates(cb: CriteriaBuilder, root: Root<T>, vararg predicateBuilders: PredicateBuilder<T>): Array<Predicate> {
        val predicates = LinkedList<Predicate>()
        if (predicateBuilders.isNotEmpty()) {
            for (builder in predicateBuilders) {
                predicates.add(builder(cb, root))
            }
        }
        return predicates.toTypedArray()
    }

    private fun <Y, X : Y> buildAttributes(criteriaUpdate: CriteriaUpdate<T>, attributesMap: Map<SingularAttribute<in T, Y>, X>) {
        attributesMap.forEach { attribute, value -> criteriaUpdate.set(attribute, value) }
    }

    protected fun findWhere(vararg predicateBuilders: PredicateBuilder<T>): List<T> {
        return createTypedQuery { criteriaBuilder, root, criteriaQuery ->
            criteriaQuery.where(*buildPredicates(criteriaBuilder, root, *predicateBuilders))
        }.resultList
    }

    protected fun <Y, X : Y> updateWhere(attributesMap: Map<SingularAttribute<in T, Y>, X>, vararg predicateBuilders: PredicateBuilder<T>) {
        createUpdateQuery { criteriaBuilder, root, criteriaUpdate ->
            buildAttributes(criteriaUpdate, attributesMap)
            criteriaUpdate.where(*buildPredicates(criteriaBuilder, root, *predicateBuilders))
        }
    }
}