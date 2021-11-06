package tech.volkov.nextdoorfarm.backend.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.volkov.nextdoorfarm.backend.model.UserLogInDto
import tech.volkov.nextdoorfarm.backend.model.UserLoggedInDto
import tech.volkov.nextdoorfarm.backend.model.UserSignInDto
import tech.volkov.nextdoorfarm.backend.retrofit.AuthRepositoryClient
import tech.volkov.nextdoorfarm.backend.retrofit.RetrofitBuilder
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class AuthRepository @Inject constructor() {

    private val client = RetrofitBuilder.retrofit.create(AuthRepositoryClient::class.java)

    suspend fun logInUser(
        userLogInDto: UserLogInDto
    ): UserLoggedInDto? = withContext(Dispatchers.IO) {
        return@withContext client.logInUser(userLogInDto).execute().body()
    }

    suspend fun signUpUser(
        userSignInDto: UserSignInDto
    ): UserLoggedInDto? = withContext(Dispatchers.IO) {
        return@withContext client.signUpUser(userSignInDto).execute().body()
    }
}
