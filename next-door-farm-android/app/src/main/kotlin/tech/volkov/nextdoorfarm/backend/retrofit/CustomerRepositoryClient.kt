package tech.volkov.nextdoorfarm.backend.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import tech.volkov.nextdoorfarm.backend.model.CustomerAndOrdersDto
import tech.volkov.nextdoorfarm.backend.model.CustomerDto

interface CustomerRepositoryClient {

    @GET("/customers")
    fun getCustomer(
        @Header(RetrofitBuilder.AUTHORIZATION_HEADER) bearerAuth: String
    ): Call<CustomerAndOrdersDto>

    @PUT("/customers")
    fun updateCustomer(
        @Header(RetrofitBuilder.AUTHORIZATION_HEADER) bearerAuth: String,
        @Body customerDto: CustomerDto
    ): Call<CustomerAndOrdersDto>
}
