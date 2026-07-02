package com.example.matchcredit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.matchcredit.R
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.UsuarioRepository
import com.example.matchcredit.domain.calculator.DiagnosticoFinanciero
import com.example.matchcredit.domain.calculator.DiagnosticoFinancieroCalculator
import com.example.matchcredit.domain.calculator.NivelDiagnosticoFinanciero
import com.example.matchcredit.domain.calculator.PrioridadRecomendacion
import com.example.matchcredit.domain.calculator.RecomendacionPerfil
import com.example.matchcredit.domain.calculator.RecomendacionPerfilCalculator
import com.example.matchcredit.domain.enums.NivelRiesgo

@Composable
fun PantallaPrincipalMatchCredit(
    usuarioId: Int,
    usuarioRepository: UsuarioRepository,
    perfilFinancieroRepository: PerfilFinancieroRepository,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = remember(usuarioId) {
        HomeViewModel(
            usuarioId = usuarioId,
            usuarioRepository = usuarioRepository,
            perfilFinancieroRepository = perfilFinancieroRepository
        )
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val diagnostico = DiagnosticoFinancieroCalculator.calcular(
        score = state.perfil?.scoreMatchcredit,
        ratioEndeudamientoActual = state.perfil?.ratioEndeudamientoActual,
        capacidadPagoDisponible = state.perfil?.capacidadPagoDisponible,
        ingresoMensual = state.perfil?.ingresoMensual
    )

    val recomendaciones = RecomendacionPerfilCalculator.generar(
        score = state.perfil?.scoreMatchcredit,
        ratioEndeudamientoActual = state.perfil?.ratioEndeudamientoActual,
        capacidadPagoDisponible = state.perfil?.capacidadPagoDisponible,
        ingresoMensual = state.perfil?.ingresoMensual,
        gastosMensuales = state.perfil?.gastosMensuales,
        cuotaMensualDeudas = state.perfil?.cuotaMensualDeudas,
        antiguedadTrabajandoMeses = state.perfil?.antiguedadTrabajandoMeses,
        tieneAhorros = state.perfil?.tieneAhorros,
        montoAhorros = state.perfil?.montoAhorros
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xff0d141d))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 88.dp)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            TopBar()

            Spacer(modifier = Modifier.height(24.dp))

            BienvenidaSection(
                nombre = state.usuario?.nombres?.split(" ")?.firstOrNull() ?: "...",
                edad = state.usuario?.edad
            )

            Spacer(modifier = Modifier.height(24.dp))

            CreditScoreCard(
                score = state.perfil?.scoreMatchcredit,
                nivelRiesgo = state.perfil?.nivelRiesgo
            )

            Spacer(modifier = Modifier.height(16.dp))

            DiagnosticoCompactoCard(
                diagnostico = diagnostico
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Resumen financiero",
                color = Color(0xffdce3f0),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            EstadisticasSection(
                ingreso = state.perfil?.ingresoMensual,
                capacidadPago = state.perfil?.capacidadPagoDisponible
            )

            Spacer(modifier = Modifier.height(12.dp))

            DeudaSection(
                cuota = state.perfil?.cuotaMensualDeudas,
                ratio = state.perfil?.ratioEndeudamientoActual
            )

            Spacer(modifier = Modifier.height(18.dp))

            RecomendacionPrincipalCard(
                recomendacion = recomendaciones.firstOrNull()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        BottomNavigationBar(
            selected = "home",
            usuarioId = usuarioId,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.container),
            contentDescription = "Logo",
            colorFilter = ColorFilter.tint(Color(0xff5af0b3)),
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "MatchCredit",
            color = Color(0xff5af0b3),
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun BienvenidaSection(
    nombre: String,
    edad: Int?
) {
    Column {
        Text(
            text = "BIENVENIDO DE NUEVO",
            color = Color(0xffbbcac0),
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Hola, $nombre",
            color = Color(0xffdce3f0),
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )

        if (edad != null) {
            Text(
                text = "$edad años",
                color = Color(0xff6b7280),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun CreditScoreCard(
    score: Int?,
    nivelRiesgo: NivelRiesgo?
) {
    val colorScore = when (nivelRiesgo) {
        NivelRiesgo.ALTA_COMPATIBILIDAD -> Color(0xff5af0b3)
        NivelRiesgo.COMPATIBILIDAD_MEDIA -> Color(0xffa8e063)
        NivelRiesgo.COMPATIBILIDAD_BAJA -> Color(0xffffc107)
        NivelRiesgo.RIESGO_ALTO -> Color(0xffef5350)
        null -> Color(0xff3c4a42)
    }

    val labelNivel = nivelRiesgo?.categoria ?: "Sin perfil financiero"
    val progreso = ((score ?: 0) / 100f).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xff192029))
            .border(
                width = 1.dp,
                color = Color(0xff3c4a42),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            text = "SALUD CREDITICIA",
            color = Color(0xffbbcac0),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = score?.toString() ?: "--",
            color = colorScore,
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = labelNivel,
            color = colorScore,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color(0xff2e353f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progreso)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(999.dp))
                    .background(colorScore)
            )
        }

        if (nivelRiesgo != null) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = nivelRiesgo.interpretacion,
                color = Color(0xff6b7280),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun DiagnosticoCompactoCard(
    diagnostico: DiagnosticoFinanciero
) {
    val colorNivel = when (diagnostico.nivel) {
        NivelDiagnosticoFinanciero.SALUDABLE -> Color(0xff5af0b3)
        NivelDiagnosticoFinanciero.MODERADO -> Color(0xffffc107)
        NivelDiagnosticoFinanciero.RIESGOSO -> Color(0xffef5350)
        NivelDiagnosticoFinanciero.INCOMPLETO -> Color(0xff6b7280)
    }

    val textoNivel = when (diagnostico.nivel) {
        NivelDiagnosticoFinanciero.SALUDABLE -> "Saludable"
        NivelDiagnosticoFinanciero.MODERADO -> "Moderado"
        NivelDiagnosticoFinanciero.RIESGOSO -> "Riesgoso"
        NivelDiagnosticoFinanciero.INCOMPLETO -> "Pendiente"
    }

    val tituloVisual = when (diagnostico.nivel) {
        NivelDiagnosticoFinanciero.SALUDABLE -> "Buen punto de partida para comparar opciones"
        NivelDiagnosticoFinanciero.MODERADO -> "Avanza con cuidado antes de endeudarte"
        NivelDiagnosticoFinanciero.RIESGOSO -> "Primero ordena tu capacidad de pago"
        NivelDiagnosticoFinanciero.INCOMPLETO -> "Completa tu perfil para recibir orientación"
    }

    val subtituloVisual = when (diagnostico.nivel) {
        NivelDiagnosticoFinanciero.SALUDABLE -> "Tu situación actual permite evaluar alternativas de crédito con menor riesgo."
        NivelDiagnosticoFinanciero.MODERADO -> "Puedes comparar opciones, pero conviene revisar monto y plazo con atención."
        NivelDiagnosticoFinanciero.RIESGOSO -> "Una nueva cuota podría afectar tu presupuesto mensual."
        NivelDiagnosticoFinanciero.INCOMPLETO -> "Registra tus datos financieros para generar un diagnóstico más útil."
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xff151c25))
            .border(
                width = 1.dp,
                color = Color(0xff3c4a42),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DIAGNÓSTICO MATCHCREDIT",
                color = Color(0xffbbcac0),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(colorNivel.copy(alpha = 0.18f))
                    .border(
                        width = 1.dp,
                        color = colorNivel,
                        shape = RoundedCornerShape(999.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = textoNivel,
                    color = colorNivel,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(58.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(colorNivel)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = tituloVisual,
                    color = Color(0xffdce3f0),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 23.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = subtituloVisual,
                    color = Color(0xffbbcac0),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xff0d141d))
                .border(
                    width = 1.dp,
                    color = Color(0xff3c4a42),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(14.dp)
        ) {
            Text(
                text = diagnostico.recomendacionPrincipal,
                color = colorNivel,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun RecomendacionPrincipalCard(
    recomendacion: RecomendacionPerfil?
) {
    if (recomendacion == null) {
        return
    }

    val prioridadColor = when (recomendacion.prioridad) {
        PrioridadRecomendacion.ALTA -> Color(0xffef5350)
        PrioridadRecomendacion.MEDIA -> Color(0xffffc107)
        PrioridadRecomendacion.BAJA -> Color(0xff5af0b3)
    }

    val prioridadTexto = when (recomendacion.prioridad) {
        PrioridadRecomendacion.ALTA -> "Alta"
        PrioridadRecomendacion.MEDIA -> "Media"
        PrioridadRecomendacion.BAJA -> "Baja"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xff151c25))
            .border(
                width = 1.dp,
                color = Color(0xff3c4a42),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(18.dp)
    ) {
        Text(
            text = "RECOMENDACIÓN PRINCIPAL",
            color = Color(0xffbbcac0),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = recomendacion.titulo,
                color = Color(0xffdce3f0),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 21.sp,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(prioridadColor)
                    .padding(horizontal = 9.dp, vertical = 5.dp)
            ) {
                Text(
                    text = prioridadTexto,
                    color = Color(0xff0d141d),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = recomendacion.descripcion,
            color = Color(0xffbbcac0),
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun EstadisticasSection(
    ingreso: Double?,
    capacidadPago: Double?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EstadisticaCard(
            titulo = "INGRESO MENSUAL",
            valor = ingreso?.let { "S/ %,.0f".format(it) } ?: "--",
            modifier = Modifier.weight(1f)
        )

        EstadisticaCard(
            titulo = "CAPACIDAD DE PAGO",
            valor = capacidadPago?.let { "S/ %,.0f".format(it) } ?: "--",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DeudaSection(
    cuota: Double?,
    ratio: Double?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EstadisticaCard(
            titulo = "CUOTA MENSUAL",
            valor = cuota?.let { "S/ %,.0f".format(it) } ?: "--",
            modifier = Modifier.weight(1f)
        )

        EstadisticaCard(
            titulo = "RATIO DEUDA",
            valor = ratio?.let { "%.1f%%".format(it * 100) } ?: "--",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun EstadisticaCard(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xff151c25))
            .border(
                width = 1.dp,
                color = Color(0xff3c4a42),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = titulo,
            color = Color(0xffbbcac0),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = valor,
            color = Color(0xffdce3f0),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun BottomNavigationBar(
    selected: String,
    usuarioId: Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xff192029))
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomItem(
            texto = "Principal",
            icon = R.drawable.ic_home,
            seleccionado = selected == "home",
            onClick = {
                if (selected != "home") {
                    navController.navigate("home/$usuarioId") {
                        launchSingleTop = true
                    }
                }
            }
        )

        BottomItem(
            texto = "Comparar",
            icon = R.drawable.ic_compare,
            seleccionado = selected == "compare",
            onClick = {
                if (selected != "compare") {
                    navController.navigate("consultaPrestamo/$usuarioId") {
                        launchSingleTop = true
                    }
                }
            }
        )

        BottomTextItem(
            texto = "Historial",
            simbolo = "▤",
            seleccionado = selected == "history",
            onClick = {
                if (selected != "history") {
                    navController.navigate("historialSimulaciones/$usuarioId") {
                        launchSingleTop = true
                    }
                }
            }
        )

        BottomItem(
            texto = "Perfil",
            icon = R.drawable.ic_profile,
            seleccionado = selected == "profile",
            onClick = {
                if (selected != "profile") {
                    navController.navigate("perfilFinanciero/$usuarioId") {
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}

@Composable
fun BottomItem(
    texto: String,
    icon: Int,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = texto,
            colorFilter = ColorFilter.tint(
                if (seleccionado) {
                    Color(0xff68fcbf)
                } else {
                    Color(0xffbbcac0)
                }
            ),
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = texto,
            color = if (seleccionado) {
                Color(0xff68fcbf)
            } else {
                Color(0xffbbcac0)
            },
            fontSize = 11.sp
        )
    }
}

@Composable
fun BottomTextItem(
    texto: String,
    simbolo: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = simbolo,
            color = if (seleccionado) {
                Color(0xff68fcbf)
            } else {
                Color(0xffbbcac0)
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = texto,
            color = if (seleccionado) {
                Color(0xff68fcbf)
            } else {
                Color(0xffbbcac0)
            },
            fontSize = 11.sp
        )
    }
}