package ec.solmedia.kotlin.jee.jpa

import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Entity
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @NotNull @Size(min = 1)
    val name: String,

    @Min(value = 0)
    val stock: Int,

    @Min(value = 0)
    val price: BigDecimal
)