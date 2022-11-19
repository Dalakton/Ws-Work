package me.project.wswork.data.remote

import me.project.wswork.data.model.CarResponse
import me.project.wswork.util.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CarsWebService {

    @GET(Constants.ENDPOINT)
    suspend fun getAllCars(): Response<List<CarResponse>>

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


