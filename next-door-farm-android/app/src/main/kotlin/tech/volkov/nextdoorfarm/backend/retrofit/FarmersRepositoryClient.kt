package tech.volkov.nextdoorfarm.backend.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import tech.volkov.nextdoorfarm.backend.model.FarmerDto

interface FarmersRepositoryClient {

    @GET("/farmers")
    fun getFarmer(
        @Header(RetrofitBuilder.AUTHORIZATION_HEADER) bearerAuth: String
    ): Call<FarmerDto>

    @GET("farmers/getAllProducts")
    fun getAllProducts(): Call<List<FarmerDto>>
}
