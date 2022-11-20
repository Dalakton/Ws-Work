package me.project.wswork.ui.register

import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.textfield.TextInputLayout
import me.project.wswork.data.workmanager.LeadWork
import me.project.wswork.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val dataCar: RegisterFragmentArgs by navArgs()
    private val viewModel: RegisterViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataCar()
        setToolbar()
        inviteLead()


        binding.buttonEntrar.setOnClickListener {
            chekingAndPassingData()
            observeState()
        }

    }

    private fun inviteLead() {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<LeadWork>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(requireContext()).enqueue(periodicWorkRequest)
    }


    private fun observeState() {
        viewModel.authenticatioState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                is AuthenticatioState.InvaliAuthentication -> {
                    // mapeando os campos nome , numero e email com as constantesque foi
                    // definida no viewModel e vamos vincular eles  para tratar o erro.
                    val validationFields: Map<String, TextInputLayout> = initValidationFields()
                    // o forEach vai pecorrer a lista fields, para ver se encontra  algum erro,
                    //Encontrando a Chave INPUT_NAME vai fazer com que ela case , com
                    // o textInputLayout da view, e logo apos passa a mensagem de erro.
                    // mapeando tudo isso das variaves do companion da viewModel e setando aquui.
                    authenticationState.fields.forEach { fielError ->
                        validationFields[fielError.first]?.error = getString(fielError.second)
                    }

                }
                is AuthenticatioState.Authenticated -> {
                    binding.editNome.text?.clear()
                    binding.editNumero.text?.clear()
                    binding.editEmail.text?.clear()

                }
            }

        })

    }

    private fun initValidationFields() = mapOf(
        RegisterViewModel.INPUT_NOME.first to binding.textInputNome,
        RegisterViewModel.INPUT_NUMERO.first to binding.textInputNumero,
        RegisterViewModel.INPUT_EMAIL.first to binding.textInputEmail
    )

    private fun chekingAndPassingData() {
        val nome = binding.editNome.text.toString().trim()
        val numero = binding.editNumero.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val idCar = dataCar.car.id

        viewModel.authentication(nome, numero, email, idCar)
    }


/*
funçao que cria o controle de navegação
e nos tras o botão de back , para nos retorna
a home fragment.
 */

    private fun setToolbar() {
        val navController = findNavController()
        val appBar = AppBarConfiguration(navController.graph)
        val toolbar = binding.toolbar
        toolbar.setupWithNavController(navController, appBar)
    }

    private fun setDataCar() {

        with(binding) {
            textMarcaCarro.text = dataCar.car.nomeDaMarca
            textModeloCarro.text = dataCar.car.nomeDoModelo
            textAnoCarro.text = dataCar.car.ano
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.textValorCarro.text =
                    NumberFormat.getInstance().format(dataCar.car.valorDoCarro)
            }
            textCorCarro.text = dataCar.car.cor
            textCombustivelCarro.text = dataCar.car.combustivel
            textNumeroPortasCarro.text = dataCar.car.quantidaDePortas
        }

    }

}


