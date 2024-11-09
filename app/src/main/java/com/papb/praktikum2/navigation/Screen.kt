package com.papb.praktikum2.navigation

sealed class Screen(val route: String) {
    object Matkul : Screen(route = "Matkul")
    object Tugas : Screen(route = "Tugas")
    object Profil : Screen(route = "Profil")
}