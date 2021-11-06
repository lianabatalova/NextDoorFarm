package tech.volkov.nextdoorfarm.backend.repository.mock

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.volkov.nextdoorfarm.backend.model.UserLogInDto
import tech.volkov.nextdoorfarm.backend.model.UserLoggedInDto
import tech.volkov.nextdoorfarm.backend.model.UserSignInDto
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class AuthRepositoryMock @Inject constructor() {

    private val usersStorage = mutableListOf<UserSignInDto>()

    suspend fun logInUser(
        userLogInDto: UserLogInDto
    ): UserLoggedInDto? = withContext(Dispatchers.IO) {
        return@withContext usersStorage
            .find { it.userName == userLogInDto.username && it.password == userLogInDto.password }
            ?.let {
                UserLoggedInDto(
                    id = 1,
                    username = it.userName,
                    userType = it.userType,
                    token = "token",
                    ttl = 10000
                )
            }
    }

    suspend fun signUpUser(
        userSignInDto: UserSignInDto
    ): UserLoggedInDto = withContext(Dispatchers.IO) {
        usersStorage.add(userSignInDto)
        return@withContext with(userSignInDto) {
            UserLoggedInDto(
                id = 1,
                username = userName,
                userType = userType,
                token = "token",
                ttl = 10000
            )
        }
    }
}
