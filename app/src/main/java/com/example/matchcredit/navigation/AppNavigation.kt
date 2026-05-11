package com.example.matchcredit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.matchcredit.screens.InicioDeSesinMatchCredit
import com.example.matchcredit.screens.RegistroMatchCredit
import com.example.matchcredit.screens.PantallaPrincipalMatchCredit

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            InicioDeSesinMatchCredit(
                onNavigateToRegistro = {
                    navController.navigate("registro")
                },
                onNavigateToHome = {
                    navController.navigate("home")
                }
            )
        }

        composable("registro") {
            RegistroMatchCredit()
        }
        composable("home") {
            PantallaPrincipalMatchCredit()
        }
    }
}