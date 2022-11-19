package me.project.wswork.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.project.wswork.ui.RegistratioViewParams

@Entity(tableName = "leadEntity")
data class LeadEntity(
    @PrimaryKey val id: Long = 0,
    val nome: String,
    val numero: String,
    val email: String,
    @SerializedName("id")
    val idCar: Int
)

    // estou convertendo o registration para um userEntity
fun RegistratioViewParams.toLeadentity(): LeadEntity{
    return  with(this){
        LeadEntity(
            nome = this.nome,
            numero = this.numero,
            email = this.email,
            idCar = this.idCar,

        )
    }
}