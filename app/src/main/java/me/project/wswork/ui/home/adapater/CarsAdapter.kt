package me.project.wswork.ui.home.adapater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import me.project.wswork.data.model.CarResponse
import me.project.wswork.databinding.ItemCarsBinding
import me.project.wswork.ui.home.HomeFragmentDirections
import java.text.NumberFormat
import java.util.*

class CarsAdapter(private val cars: List<CarResponse>) :
    RecyclerView.Adapter<CarsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding = ItemCarsBinding.inflate(layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = cars[position]

        holder.binding.textMarcaCarro.text = car.nomeDaMarca
        holder.binding.textModeloCarro.text = car.nomeDoModelo
        holder.binding.textAnoCarro.text = car.ano
        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        val valorDouble = car.valorDoCarro.toDouble()
        if (valorDouble < 1000) {
            holder.binding.textValorCarro.text = formatador.format(valorDouble * 1000).toString()
        } else {
            holder.binding.textValorCarro.text = formatador.format(valorDouble).toString()
        }
        holder.binding.textCombustivelCarro.text = car.combustivel

        holder.itemView.setOnClickListener {

            //Navegação da homeFragment para a registerFragment , passanddo o car como argumento
            val navigation = HomeFragmentDirections.actionHomeFragmentToRegisterFragment(car)
            Navigation.findNavController(it).navigate(navigation)
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    class ViewHolder(val binding: ItemCarsBinding) : RecyclerView.ViewHolder(binding.root)

}
