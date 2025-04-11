package com.selly0024.assesment1.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object About : Screen("aboutScreen")
}