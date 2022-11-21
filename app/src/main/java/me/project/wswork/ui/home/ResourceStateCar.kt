package me.project.wswork.ui.home

import me.project.wswork.data.model.CarResponse

/*
*classe armazenadora dos estados
* selead class , uma classe abstrata que impede de ser herdada por outras classes
* então pode ser instanciada varias vezes e armazenar varios estados diferentes em determinadas
* instancias.
*/
sealed class ResourceStateCar<T>(
    val data: List<CarResponse>? = null,
    val message: String? = null
) {
    class Sucess<T>(data: List<CarResponse>) : ResourceStateCar<T>(data)
    class Error<T>(message: String, data: List<CarResponse>? = null) :
        ResourceStateCar<T>(data, message)

    class Loading<T> : ResourceStateCar<T>()

}
