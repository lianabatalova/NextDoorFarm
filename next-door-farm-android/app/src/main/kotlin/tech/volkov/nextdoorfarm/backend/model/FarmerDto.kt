package tech.volkov.nextdoorfarm.backend.model

data class FarmerDto(
    val id: String,
    val firstName: String,
    val secondName: String,
    val address: String?,
    val username: String,
    val email: String,
    val phone: String?,
    val description: String,
    val rating: Long,
    val products: List<Product>
)