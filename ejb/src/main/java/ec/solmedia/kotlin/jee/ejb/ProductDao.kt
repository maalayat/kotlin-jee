package ec.solmedia.kotlin.jee.ejb

import ec.solmedia.kotlin.jee.jpa.Product
import ec.solmedia.kotlin.jee.jpa.Product_
import javax.ejb.Stateless

@Stateless
class ProductDao : AbstractDao<Product>(Product::class.java) {

    fun findAllByJpql(): List<Product> {
        val query = em.createQuery("SELECT p FROM Product p", Product::class.java)
        return query.resultList
    }

    fun findByStock() : List<Product> {
        val byStock : PredicateBuilder<Product> = {
            criteriaBuilder, root ->  criteriaBuilder.gt(root.get(Product_.stock), 10)
        }

        return findWhere(byStock)
    }
}