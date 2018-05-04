package ec.solmedia.kotlin.jee.ejb

import ec.solmedia.kotlin.jee.jpa.Product
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class ProductDao {

    @PersistenceContext(name = "primary")
    private lateinit var em: EntityManager

    fun findProducts(): List<Product> {
        println("findProductsDao")
        val query = em.createQuery("SELECT p FROM Product p", Product::class.java)
        return query.resultList
    }
}