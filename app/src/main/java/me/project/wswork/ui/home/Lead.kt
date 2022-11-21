package me.project.wswork.ui.home

import com.google.gson.annotations.SerializedName

data class Lead(
    val nome: String = "",
    val numero: String = "",
    val email: String = "",
    val idCar: Int = 0
)