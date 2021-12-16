package tech.volkov.nextdoorfarm.backend.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.volkov.nextdoorfarm.backend.model.*
import tech.volkov.nextdoorfarm.backend.retrofit.CustomersRepositoryClient
import tech.volkov.nextdoorfarm.backend.retrofit.RetrofitBuilder
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class CustomerRepository @Inject constructor() {

    private val client = RetrofitBuilder.retrofit.create(CustomersRepositoryClient::class.java)

    suspend fun getCustomer(token: String): CustomerAndOrdersDto? = withContext(Dispatchers.IO) {
        return@withContext try {
            client.getCustomer("Bearer $token").execute().body()
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun updateCustomer(
        token: String,
        customerDto: CustomerDto
    ): CustomerAndOrdersDto? = withContext(Dispatchers.IO) {
        return@withContext try {
            client.updateCustomer("Bearer $token", customerDto).execute().body()
        } catch (ex: Exception) {
            null
        }
    }
}
