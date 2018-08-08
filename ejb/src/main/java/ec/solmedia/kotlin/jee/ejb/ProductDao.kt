package ec.solmedia.kotlin.jee.ejb

import com.mysema.query.jpa.impl.JPAQuery
import ec.solmedia.kotlin.jee.jpa.Product
import ec.solmedia.kotlin.jee.jpa.QProduct.product
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class ProductDao {

    @PersistenceContext(name = "primary")
    private lateinit var em: EntityManager

    fun getProducts(): List<Product> = JPAQuery(em).from(product).list(product)

    fun findByStock(stock: Int = 10): List<Product> {
        return JPAQuery(em)
                .from(product)
                .where(product.stock.gt(stock))
                .list(product)
    }
}