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
import com.example.matchcredit.ui.screens.ConsultaPrestamoScreen
import com.example.matchcredit.ui.screens.ResultadosPrestamoScreen
import com.example.matchcredit.ui.screens.DetallePrestamoScreen
import com.example.matchcredit.ui.screens.HistorialSimulacionesScreen

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
                navController = navController,
                usuarioRepository = appContainer.usuarioRepository
            )
        }

        composable(
            route = "home/{usuarioId}",
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0

            PantallaPrincipalMatchCredit(
                usuarioId = usuarioId,
                usuarioRepository = appContainer.usuarioRepository,
                perfilFinancieroRepository = appContainer.perfilFinancieroRepository,
                navController = navController
            )
        }

        composable(
            route = "perfilFinanciero/{usuarioId}",
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0

            PerfilFinancieroScreen(
                usuarioId = usuarioId,
                perfilFinancieroRepository = appContainer.perfilFinancieroRepository,
                usuarioRepository = appContainer.usuarioRepository,
                navController = navController
            )
        }

        composable(
            route = "consultaPrestamo/{usuarioId}",
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0

            ConsultaPrestamoScreen(
                usuarioId = usuarioId,
                navController = navController
            )
        }

        composable(
            route = "resultadosPrestamo/{usuarioId}/{tipoPrestamo}/{monto}/{plazo}",
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.IntType },
                navArgument("tipoPrestamo") { type = NavType.StringType },
                navArgument("monto") { type = NavType.FloatType },
                navArgument("plazo") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
            val tipoPrestamo = backStackEntry.arguments?.getString("tipoPrestamo") ?: "Personal"
            val monto = backStackEntry.arguments?.getFloat("monto")?.toDouble() ?: 0.0
            val plazo = backStackEntry.arguments?.getInt("plazo") ?: 0

            ResultadosPrestamoScreen(
                usuarioId = usuarioId,
                tipoPrestamo = tipoPrestamo,
                montoSolicitado = monto,
                plazoMeses = plazo,
                perfilFinancieroRepository = appContainer.perfilFinancieroRepository,
                productoCrediticioRepository = appContainer.productoCrediticioRepository,
                bancoRepository = appContainer.bancoRepository,
                navController = navController
            )
        }

        composable(
            route = "detallePrestamo/{usuarioId}/{productoId}/{monto}/{plazo}/{ranking}",
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.IntType },
                navArgument("productoId") { type = NavType.IntType },
                navArgument("monto") { type = NavType.FloatType },
                navArgument("plazo") { type = NavType.IntType },
                navArgument("ranking") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            val monto = backStackEntry.arguments?.getFloat("monto")?.toDouble() ?: 0.0
            val plazo = backStackEntry.arguments?.getInt("plazo") ?: 0
            val ranking = backStackEntry.arguments?.getInt("ranking") ?: 0

            DetallePrestamoScreen(
                usuarioId = usuarioId,
                productoId = productoId,
                montoSolicitado = monto,
                plazoMeses = plazo,
                ranking = ranking,
                perfilFinancieroRepository = appContainer.perfilFinancieroRepository,
                productoCrediticioRepository = appContainer.productoCrediticioRepository,
                bancoRepository = appContainer.bancoRepository,
                resultadoGuardadoRepository = appContainer.resultadoGuardadoRepository,
                navController = navController
            )
        }

        composable(
            route = "historialSimulaciones/{usuarioId}",
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0

            HistorialSimulacionesScreen(
                usuarioId = usuarioId,
                resultadoGuardadoRepository = appContainer.resultadoGuardadoRepository,
                navController = navController
            )
        }
    }
}