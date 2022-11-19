package me.project.wswork.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.project.wswork.data.model.CarResponse
import me.project.wswork.data.repositories.CarsRepository
import retrofit2.Response
import java.io.IOException

class CarsViewModel(private val repository: CarsRepository) : ViewModel() {

    private val _carsList = MutableStateFlow<ResourceStateCar<CarResponse>>(ResourceStateCar.Loading())
    val carList: StateFlow<ResourceStateCar<CarResponse>> = _carsList

    init {
        getAllCars()
    }


    private fun getAllCars() = viewModelScope.launch {
        getResponse()

    }
    // função que pega a resposta da api , joga para a função de tratamento e retorna a resposta
    private suspend fun getResponse() {
        try {
            val response = repository.getAllCars()
            _carsList.value = handleResponse(response)
        } catch (t: Throwable) {
            when (t) {
                // Erros genericos que podem acontecer
                is IOException -> _carsList.value =
                    ResourceStateCar.Error("Erro de conecão com a internet")
                else -> _carsList.value = ResourceStateCar.Error("Falha na conexão de dados")
            }
        }
    }
    // função de tratamento da resposata da api
    // caso sendo .sucesso envio o resultado e o estado.
    private fun handleResponse(response: Response<List<CarResponse>>): ResourceStateCar<CarResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResourceStateCar.Sucess(it)
            }
        }
        return ResourceStateCar.Error(response.message())
    }


}