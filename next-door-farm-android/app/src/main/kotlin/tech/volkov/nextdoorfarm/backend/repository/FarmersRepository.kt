package tech.volkov.nextdoorfarm.backend.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.volkov.nextdoorfarm.backend.model.*
import tech.volkov.nextdoorfarm.backend.retrofit.CustomersRepositoryClient
import tech.volkov.nextdoorfarm.backend.retrofit.FarmersRepositoryClient
import tech.volkov.nextdoorfarm.backend.retrofit.RetrofitBuilder
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class FarmersRepository @Inject constructor() {

    private val client = RetrofitBuilder.retrofit.create(FarmersRepositoryClient::class.java)

    suspend fun getAllProducts(): List<FarmerDto>? = withContext(Dispatchers.IO) {
        return@withContext try {
            client.getAllProducts().execute().body()
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun getFarmer(token: String) = withContext(Dispatchers.IO) {
        return@withContext try {
            client.getFarmer("Bearer $token").execute().body()
        } catch (ex: Exception) {
            null
        }
    }
}
