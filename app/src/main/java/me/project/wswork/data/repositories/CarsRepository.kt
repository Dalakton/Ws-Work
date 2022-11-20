package me.project.wswork.data.repositories

import androidx.lifecycle.LiveData
import me.project.wswork.data.local.LeadEntity
import me.project.wswork.data.model.CarResponse
import me.project.wswork.ui.Lead
import retrofit2.Response

interface CarsRepository {

   suspend fun getAllCars(): Response<List<CarResponse>>

   suspend fun insert(registationViewParams: Lead)

   suspend fun getLeads(): List<LeadEntity>

   suspend fun inviteLeadsApi(leads: List<LeadEntity>) : Boolean


}