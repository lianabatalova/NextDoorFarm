package tech.volkov.nextdoorfarm.backend.model

data class UserLogInDto(
    val username: String,
    val password: String,
    val userType: UserType
)

data class UserSignInDto(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val email: String,
    val password: String,
    val userType: UserType
)

data class UserLoggedInDto(
    val id: String,
    val username: String,
    val userType: UserType,
    val token: String,
    val ttl: Long
)

enum class UserType {
    customer, farmer
}
