package me.project.wswork.data.repositories

import me.project.wswork.data.local.CarDao
import me.project.wswork.data.remote.CarsWebService
import me.project.wswork.data.local.LeadEntity
import me.project.wswork.data.model.CarResponse
import me.project.wswork.ui.RegistratioViewParams
import retrofit2.Response

interface CarsRepository {

   suspend fun getAllCars(): Response<List<CarResponse>>

   suspend fun insert(registationViewParams: RegistratioViewParams)


}