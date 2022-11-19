package me.project.wswork.ui

import com.google.gson.annotations.SerializedName

data class RegistratioViewParams(
    val nome: String = "",
    val numero: String = "",
    val email: String = "",
    val idCar: Int = 0
)