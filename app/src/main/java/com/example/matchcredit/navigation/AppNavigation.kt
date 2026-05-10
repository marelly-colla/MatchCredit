package com.example.matchcredit.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.matchcredit.screens.RegistroMatchCredit

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {

        composable("home") {
            Text("Pantalla Home")
        }

        composable("registro") {
            RegistroMatchCredit()
        }
    }
}