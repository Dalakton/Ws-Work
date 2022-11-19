package me.project.wswork.data.repositories

import me.project.wswork.data.local.CarDao
import me.project.wswork.data.local.toLeadentity
import me.project.wswork.data.remote.CarsWebService
import me.project.wswork.ui.RegistratioViewParams

/*
essa classe é que da acesso a nossas fontes de dados, nos ajudando na
manutenção e desacoplamento do codigo, caso eu queria mudar os dados
de acesso ao bando de dados ,é só modificar na classe que esta sendo implementada.
 */

class ResourseCarRepository(
    private val carWebService: CarsWebService,
    private val dao: CarDao,
) : CarsRepository {

    override suspend fun getAllCars() = carWebService.getAllCars()

    override suspend fun insert(registationViewParams: RegistratioViewParams) {
        val leadEntity = registationViewParams.toLeadentity()
        dao.insert(leadEntity)
    }
}