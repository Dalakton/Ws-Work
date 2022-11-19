package me.project.wswork.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CarResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("marca_id")
    val marcaId: Int,
    @SerializedName("marca_nome")
    val nomeDaMarca: String,
    @SerializedName("nome_modelo")
    val nomeDoModelo: String,
    @SerializedName("ano")
    val ano: String,
    @SerializedName("combustivel")
    val combustivel: String,
    @SerializedName("num_portas")
    val quantidaDePortas: String,
    @SerializedName("valor_fipe")
    val valorDoCarro: Double,
    @SerializedName("cor")
    val cor: String,
    @SerializedName("timestamp_cadastro")
    val tempoDeCadastro: String,
): Serializable
