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
import me.project.wswork.ui.RegistratioViewParams

class RegisterViewModel(private val repository: CarsRepository) : ViewModel() {

    private var _authenticationState = MutableLiveData<AuthenticatioState>()
    val authenticatioState: LiveData<AuthenticatioState> = _authenticationState


    fun authentication(nome: String, numero: String, email: String, idCar: Int) {

        if (isValidData(nome, numero, email, idCar)) {
            _authenticationState.value = AuthenticatioState.Authenticated

            val registratioViewParams =
                RegistratioViewParams(idCar = idCar, nome = nome, numero = numero, email = email)

            viewModelScope.launch(Dispatchers.Default) {
                repository.insert(registratioViewParams)
            }

        }
    }

    private fun isValidData(nome: String, numero: String, email: String, idCar: Int): Boolean {
        val invalidFiels = arrayListOf<Pair<String, Int>>()

        if (nome.isEmpty() && nome.length <= 3) {
            invalidFiels.add(INPUT_NOME)
        }

        if (numero.isEmpty() && numero.length <= 8) {
            invalidFiels.add(INPUT_NUMERO)
        }

        if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            invalidFiels.add(INPUT_EMAIL)
        }

        //validando e verificando se a lista esta ou não vazia
        if (invalidFiels.isNotEmpty()) {
            // passamos a lista de campos invalidos , caso o usuario não colloque os dados corretos
            _authenticationState.value = AuthenticatioState.InvaliAuthentication(invalidFiels)
            return false
        }
        return true

    }

    //INPUT_NOME é uma chave que vai mapear para uma mensagem de erro.
    // modelo PAir<string, int>
    companion object {
        val INPUT_NOME = "INPUT_NOME" to R.string.register_input_layou_error_invalid_name
        val INPUT_NUMERO = "INPUT_NUMERO" to R.string.register_input_layou_error_invalid_numero
        val INPUT_EMAIL = "INPUT_Email" to R.string.register_input_layou_error_invalid_email
    }
}
/*




 */