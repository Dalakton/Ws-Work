package me.project.wswork.ui.register

import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.textfield.TextInputLayout
import me.project.wswork.R
import me.project.wswork.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


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


        binding.buttonEntrar.setOnClickListener {
            passingData()
            observeState()
        }

        // FUNCIONALIDADE de voltar para a tela inicial
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.homeFragment, false)


        }

    }

    //Observando o status da validação do usuario e retornado sua devida reposta
    //caso obtenha sucesso ou erro será enviado a mensagem de erro para caso ocorra um erro
    //obtendo sucesso ele é registrado ao banco de dados e enviado  a home fragment
    private fun observeState() {
        viewModel.validationState.observe(viewLifecycleOwner, Observer { validationState ->
            when (validationState) {
                is ValidationStateRegister.InvalidValidation -> {
                    // mapeando os campos nome , numero e email com as constantesque foi
                    // definida no viewModel e vamos vincular eles  para tratar o erro.
                    val validationFields: Map<String, TextInputLayout> = initValidationFields()
                    // o forEach vai pecorrer a lista fields, para ver se encontra  algum erro,
                    //Encontrando a Chave INPUT_NAME vai fazer com que ela case , com
                    // o textInputLayout da view, e logo apos passa a mensagem de erro.
                    // mapeando tudo isso das variaves do companion da viewModel e setando aquui.
                    validationState.fields.forEach { fielError ->
                        validationFields[fielError.first]?.error = getString(fielError.second)
                    }

                }
                //limpando os campos e retornando a home fragment
                is ValidationStateRegister.Authenticated -> {
                    binding.editNome.text?.clear()
                    binding.editNumero.text?.clear()
                    binding.editEmail.text?.clear()

                    findNavController().navigate(R.id.homeFragment)
                    Toast.makeText(requireContext(), R.string.sucesso, Toast.LENGTH_LONG).show()


                }
            }

        })

    }

    // mapeando a chave com o inputLayout do editText , para da sua devida mensagem de erro.
    private fun initValidationFields() = mapOf(
        RegisterViewModel.INPUT_NAME.first to binding.textInputNome,
        RegisterViewModel.INPUT_NUMBER.first to binding.textInputNumero,
        RegisterViewModel.INPUT_EMAIL.first to binding.textInputEmail
    )

    // passando dados para validação e dependendo do retorno
    // será tomda uma ação , caso erro retornara o status e será notificado
    // caso sucesso em sua validação será feio o registro no banco de dados
    private fun passingData() {
        val name = binding.editNome.text.toString().trim()
        val number = binding.editNumero.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val idCar = dataCar.car.id

        viewModel.insertDb(name, number, email, idCar)
    }

/*
funçao abaixo  cria o controle de navegação
e nos tras o botão de back , para retorna
a home fragment.
 */

    private fun setToolbar() {
        val navController = findNavController()
        val appBar = AppBarConfiguration(navController.graph)
        val toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setupWithNavController(navController, appBar)
    }


    // recebendo os dados passado e populando nesta fragment
    private fun setDataCar() {
        with(binding) {
            textMarcaCarro.text = dataCar.car.nomeDaMarca
            textModeloCarro.text = dataCar.car.nomeDoModelo
            textAnoCarro.text = dataCar.car.ano
            val formatador = java.text.NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            val valorDouble = dataCar.car.valorDoCarro.toDouble()
            if (valorDouble < 1000) {
                textValorCarro.text = formatador.format(valorDouble * 1000).toString()
            } else {
                textValorCarro.text = formatador.format(valorDouble).toString()
            }
            textCorCarro.text = dataCar.car.cor
            textCombustivelCarro.text = dataCar.car.combustivel
            textNumeroPortasCarro.text = dataCar.car.quantidaDePortas
        }

    }


}


