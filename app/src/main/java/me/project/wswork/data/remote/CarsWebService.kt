package me.project.wswork.data.remote

import androidx.lifecycle.LiveData
import me.project.wswork.data.LeadRequest
import me.project.wswork.data.local.LeadEntity
import me.project.wswork.data.model.CarResponse
import me.project.wswork.util.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CarsWebService {

    @GET(Constants.ENDPOINT)
    suspend fun getAllCars(): Response<List<CarResponse>>

    @POST("cars/leads")
    suspend fun setLeads(@Body lead: List<LeadEntity>): Response<LeadRequest>

    companion object {

        private val carsWebService: CarsWebService by lazy {

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(CarsWebService::class.java)
        }

        fun getInstance(): CarsWebService {
            return carsWebService
        }
    }

}


