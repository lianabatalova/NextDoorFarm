package tech.volkov.nextdoorfarm.backend.model

data class CustomerAndOrdersDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val address: String,
    val username: String,
    val email: String,
    val phone: String,
    val orders: List<CustomerOrder>
)

data class CustomerOrder(
    val id: Long,
    val products: List<Product>,
    val status: OrderStatus = OrderStatus.FILLING_IN
)

enum class OrderStatus {
    FILLING_IN,
    SUBMITTED,
    CANCELED
}

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val pricePerKg: Float,
    val imageLink: String,
    val amount: Int
)

data class CustomerDto(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val address: String,
    val username: String,
    val email: String,
    val phone: String
)
