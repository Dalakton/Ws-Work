package me.project.wswork.ui.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.project.wswork.R
import me.project.wswork.data.local.CarDatabase
import me.project.wswork.data.remote.CarsWebService
import me.project.wswork.data.repositories.CarsRepository
import me.project.wswork.data.repositories.ResourseCarRepository
import me.project.wswork.databinding.FragmentHomeBinding
import me.project.wswork.ui.home.adapater.CarsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CarsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        optionsRecycler()
        colletObserve()
    }

    //Observando e coletando o status da requisição da api para da sua devida colocação
    //caso seja sucesso vamos fazer o adaptamento ao recycler view e inserir os dados
    //em seu devido lugar, e desabilitaremos a progress bar
    private fun colletObserve() = lifecycleScope.launch {
        viewModel.carList.collect { resource ->
            when (resource) {
                is ResourceStateCar.Sucess -> {
                    resource.data?.let {
                        binding.progressCircular.visibility = View.INVISIBLE
                        val carsAdapter = CarsAdapter(it)
                        binding.recyclerCars.adapter = carsAdapter

                    }
                }
                //caso seja erro a progress bar sera desabilitada e sera nos dado uma mensagem de erro
                is ResourceStateCar.Error -> {
                    resource.message?.let {
                        binding.progressCircular.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
                        Log.i("homeFragment", "Error")
                    }
                }
                // em caso de loading será visivel a nossa progress bar
                is ResourceStateCar.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE

                }
            }
        }
    }

    // dados opcionais do recyclerView
    private fun optionsRecycler() {
        binding.recyclerCars.setHasFixedSize(true)
        binding.recyclerCars.layoutManager = LinearLayoutManager(requireContext())
    }


}


