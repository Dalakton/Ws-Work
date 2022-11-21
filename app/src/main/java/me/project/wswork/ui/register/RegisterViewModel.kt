package me.project.wswork.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.project.wswork.R
import me.project.wswork.data.repositories.CarsRepository
import me.project.wswork.ui.home.Lead

class RegisterViewModel(private val repository: CarsRepository) : ViewModel() {

    private var _validationState = MutableLiveData<ValidationStateRegister>()
    val validationState: LiveData<ValidationStateRegister> = _validationState


    fun insertDb(nome: String, numero: String, email: String, idCar: Int) {

            // verificando se deu suceso na validação para retorna o status de valido para a ui
        if (isValidData(nome, numero, email, idCar)) {
            _validationState.value = ValidationStateRegister.Authenticated

           val lead =
                Lead(idCar = idCar, nome = nome, numero = numero, email = email)

                // obtémos sucesso na validação hora de inserir ao banco de dados
                // usando o escopo de coroutines  para excutar em uma outra thread
                // deixando o fluxo de dados da main thread limpa e evitando travamentos.

            viewModelScope.launch(Dispatchers.IO) {
                repository.insert(lead)
            }

        }
    }

    // função de validação dos dados passados
    private fun isValidData(nome: String, numero: String, email: String, idCar: Int): Boolean {
        val invalidFiels = arrayListOf<Pair<String, Int>>()

        if (nome.isEmpty() || nome.length <= 3) {
            invalidFiels.add(INPUT_NAME)
        }

        if (numero.isEmpty() || numero.length <= 8) {
            invalidFiels.add(INPUT_NUMBER)
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            invalidFiels.add(INPUT_EMAIL)
        }

        //validando e verificando se a lista esta ou não vazia
        if (invalidFiels.size > 0) {
            // passamos a lista de campos invalidos , caso o usuario não coloque os dados corretos
            _validationState.value = ValidationStateRegister.InvalidValidation(invalidFiels)
            return false
        }
        return true

    }

    //INPUT_NOME é uma chave que vai mapear para uma mensagem de erro.
    // modelo PAir<string, int>
    companion object {
        val INPUT_NAME = "INPUT_NAME" to R.string.register_input_layou_error_invalid_name
        val INPUT_NUMBER = "INPUT_NUMBER" to R.string.register_input_layou_error_invalid_numero
        val INPUT_EMAIL = "INPUT_Email" to R.string.register_input_layou_error_invalid_email
    }
}
/*




 */