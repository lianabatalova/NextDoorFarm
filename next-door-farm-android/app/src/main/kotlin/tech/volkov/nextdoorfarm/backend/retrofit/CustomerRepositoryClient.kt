package tech.volkov.nextdoorfarm.backend.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import tech.volkov.nextdoorfarm.backend.model.CustomerAndOrdersDto
import tech.volkov.nextdoorfarm.backend.model.CustomerDto

interface CustomerRepositoryClient {

    @GET("/api/v1/customers")
    fun getCustomer(): Call<CustomerAndOrdersDto>

    @PUT("/api/v1/customers")
    fun updateCustomer(@Body customerDto: CustomerDto): Call<CustomerAndOrdersDto>

}
