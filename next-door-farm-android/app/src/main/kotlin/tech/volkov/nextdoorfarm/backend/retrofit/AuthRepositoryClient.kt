package tech.volkov.nextdoorfarm.backend.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import tech.volkov.nextdoorfarm.backend.model.UserLogInDto
import tech.volkov.nextdoorfarm.backend.model.UserLoggedInDto
import tech.volkov.nextdoorfarm.backend.model.UserSignInDto

interface AuthRepositoryClient {

    @POST("/log-in")
    fun logInUser(@Body userLogInDto: UserLogInDto): Call<UserLoggedInDto>

    @POST("/sign-up")
    fun signUpUser(@Body userSignInDto: UserSignInDto): Call<UserLoggedInDto>

}
