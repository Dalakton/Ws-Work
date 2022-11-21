package me.project.wswork.data.repositories

import me.project.wswork.data.local.LeadEntity
import me.project.wswork.data.model.CarResponse
import me.project.wswork.ui.home.Lead
import retrofit2.Response

//nossa interface que com este metodos da acesso aos seus devidos retornos,
//trazendo segurança ao nosso codigo por so expor a interface e não a implementação
//e desacoplamento do codigo.
interface CarsRepository {

   suspend fun getAllCars(): Response<List<CarResponse>>

   suspend fun insert(registationViewParams: Lead)

   suspend fun getLeads(): List<LeadEntity>

   suspend fun inviteLeadsApi(leads: List<LeadEntity>):Response<List<LeadEntity>>


}