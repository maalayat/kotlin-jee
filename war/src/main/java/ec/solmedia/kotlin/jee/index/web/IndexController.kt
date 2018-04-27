package ec.solmedia.kotlin.jee.index.web

import ec.solmedia.kotlin.jee.index.service.ProductService
import java.io.Serializable
import javax.enterprise.inject.Model
import javax.inject.Inject

@Model
class IndexController : Serializable {

    @Inject
    private lateinit var productService: ProductService

    fun getProducts() = productService.getProducts()
}