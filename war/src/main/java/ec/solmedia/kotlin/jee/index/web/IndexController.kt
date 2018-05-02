package ec.solmedia.kotlin.jee.index.web

import ec.solmedia.kotlin.jee.index.service.ProductService
import java.io.Serializable
import javax.annotation.PostConstruct
import javax.enterprise.inject.Model
import javax.inject.Inject

@Model
class IndexController : Serializable {

    @Inject
    private lateinit var productService: ProductService

    @PostConstruct
    fun init() {
        productService.getProducts().forEach { println(it.name) }
    }


    fun getApp() = "Kotlin supermarket"
}