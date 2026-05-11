package com.example.matchcredit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.matchcredit.screens.InicioDeSesinMatchCredit
import com.example.matchcredit.screens.RegistroMatchCredit

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            InicioDeSesinMatchCredit(
                onNavigateToRegistro = {
                    navController.navigate("registro")
                }
            )
        }

        composable("registro") {
            RegistroMatchCredit()
        }
    }
}