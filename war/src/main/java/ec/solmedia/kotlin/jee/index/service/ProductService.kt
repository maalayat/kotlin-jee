package ec.solmedia.kotlin.jee.index.service

import ec.solmedia.kotlin.jee.ejb.ProductDao
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class ProductService {

    @Inject
    private lateinit var productDao: ProductDao

    fun getProducts() = productDao.findByStock()
}