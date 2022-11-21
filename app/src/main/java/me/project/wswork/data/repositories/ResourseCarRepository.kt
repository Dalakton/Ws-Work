package me.project.wswork.data.repositories

import me.project.wswork.data.local.CarDao
import me.project.wswork.data.local.LeadEntity
import me.project.wswork.data.local.toLeadentity
import me.project.wswork.data.remote.CarsWebService
import me.project.wswork.ui.home.Lead
import retrofit2.Response

/*
essa classe é nossa fontes de dados, nos ajudando na
manutenção e desacoplamento do codigo, caso eu queria mudar os dados
de acesso a determinada função ,é só modificar na classe que esta sendo implementada.
 */

class ResourseCarRepository(
    private val carWebService: CarsWebService,
    private val dao: CarDao,
) : CarsRepository {

    override suspend fun getAllCars() = carWebService.getAllCars()

    override suspend fun insert(lead: Lead) {
        val lead = lead.toLeadentity()
        dao.insert(lead)
    }

    override suspend fun getLeads(): List<LeadEntity> = dao.getLeads()

    override suspend fun inviteLeadsApi(leads: List<LeadEntity>):Response<List<LeadEntity>> {

        return carWebService.setLeads(leads)
    }

}
