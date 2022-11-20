package me.project.wswork.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.project.wswork.ui.Lead

@Entity(tableName = "leadEntity")
data class LeadEntity(
    @PrimaryKey val id: Long = 0,
    val nome: String,
    val numero: String,
    val email: String,
    @SerializedName("id")
    val idCar: Int
)

    // estou convertendo o lead para um leadEntity
fun Lead.toLeadentity(): LeadEntity{
    return LeadEntity(
            nome = this.nome,
            numero = this.numero,
            email = this.email,
            idCar = this.idCar,

        )
    }
