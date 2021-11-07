package tech.volkov.nextdoorfarm.backend.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.volkov.nextdoorfarm.backend.model.*
import tech.volkov.nextdoorfarm.backend.retrofit.CustomerRepositoryClient
import tech.volkov.nextdoorfarm.backend.retrofit.RetrofitBuilder
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class CustomerRepository @Inject constructor() {

    private val client = RetrofitBuilder.retrofit.create(CustomerRepositoryClient::class.java)

    suspend fun getCustomer(): CustomerAndOrdersDto? = withContext(Dispatchers.IO) {
        return@withContext try {
            client.getCustomer().execute().body()
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun updateCustomer(
        customerDto: CustomerDto
    ): CustomerAndOrdersDto? = withContext(Dispatchers.IO) {
        return@withContext try {
            client.updateCustomer(customerDto).execute().body()
        } catch (ex: Exception) {
            null
        }
    }
}
