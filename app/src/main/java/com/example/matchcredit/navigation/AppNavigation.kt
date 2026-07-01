package com.example.matchcredit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.matchcredit.data.AppContainer
import com.example.matchcredit.ui.screens.InicioDeSesinMatchCredit
import com.example.matchcredit.ui.screens.RegistroMatchCredit
import com.example.matchcredit.ui.screens.PantallaPrincipalMatchCredit
import com.example.matchcredit.ui.screens.PerfilFinancieroScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    appContainer: AppContainer
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            InicioDeSesinMatchCredit(
                usuarioRepository = appContainer.usuarioRepository,
                onNavigateToRegistro = {
                    navController.navigate("registro")
                },
                onNavigateToHome = { usuarioId ->
                    navController.navigate("home/$usuarioId")
                }
            )
        }

        composable("registro") {
            RegistroMatchCredit(
                navController=navController,
                usuarioRepository = appContainer.usuarioRepository
            )
        }

        composable(
            route = "home/{usuarioId}",
            arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
            PantallaPrincipalMatchCredit(
                usuarioId = usuarioId,
                usuarioRepository = appContainer.usuarioRepository,
                perfilFinancieroRepository = appContainer.perfilFinancieroRepository
            )
        }

        composable(
            route = "perfilFinanciero/{usuarioId}",
            arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0

            PerfilFinancieroScreen(
                usuarioId = usuarioId,
                perfilFinancieroRepository = appContainer.perfilFinancieroRepository,
                usuarioRepository = appContainer.usuarioRepository,
                onGuardadoExitoso = {
                    navController.navigate("home/$usuarioId") {
                        popUpTo("perfilFinanciero/{usuarioId}") { inclusive = true }
                    }
                }
            )
        }
    }
}