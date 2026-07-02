package com.example.matchcredit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.matchcredit.data.dto.ResultadoGuardadoDetalle
import com.example.matchcredit.data.repository.ResultadoGuardadoRepository
import com.example.matchcredit.domain.calculator.RiesgoPrestamoCalculator

@Composable
fun DetalleSimulacionGuardadaScreen(
    usuarioId: Int,
    resultadoId: Int,
    resultadoGuardadoRepository: ResultadoGuardadoRepository,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = remember(resultadoId) {
        DetalleSimulacionGuardadaViewModel(
            resultadoId = resultadoId,
            resultadoGuardadoRepository = resultadoGuardadoRepository
        )
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xff0d141d))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 82.dp)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            TopBar()

            Spacer(modifier = Modifier.height(28.dp))

            DetalleGuardadoHeader(
                onBackClick = {
                    navController.popBackStack()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Esta información corresponde a una simulación guardada anteriormente.",
                color = Color(0xffbbcac0),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            when {
                state.cargando -> {
                    CircularProgressIndicator(
                        color = Color(0xff5af0b3)
                    )
                }

                state.error != null -> {
                    DetalleMensajeCard(
                        titulo = "No se pudo cargar",
                        texto = state.error ?: "",
                        esError = true
                    )
                }

                state.detalle != null -> {
                    DetalleGuardadoContenido(
                        detalle = state.detalle!!
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        BottomNavigationBar(
            selected = "history",
            usuarioId = usuarioId,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun DetalleGuardadoHeader(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xff151c25))
                .border(
                    width = 1.dp,
                    color = Color(0xff5af0b3),
                    shape = RoundedCornerShape(14.dp)
                )
                .clickable {
                    onBackClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "←",
                color = Color(0xff5af0b3),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Detalle guardado",
            color = Color(0xffdce3f0),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DetalleGuardadoContenido(
    detalle: ResultadoGuardadoDetalle
) {
    val estadoColor = when {
        detalle.cumpleFiltros && detalle.cumpleCapacidadPago -> Color(0xff5af0b3)
        detalle.cumpleFiltros -> Color(0xffffc107)
        else -> Color(0xffef5350)
    }

    val estadoTexto = when {
        detalle.cumpleFiltros && detalle.cumpleCapacidadPago -> "Guardado recomendado"
        detalle.cumpleFiltros -> "Guardado para revisar"
        else -> "Guardado no ideal"
    }

    val riesgo = RiesgoPrestamoCalculator.calcular(
        ratioPostCredito = detalle.ratioPostCredito
    )

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
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "#${detalle.ranking} ${detalle.nombreBanco}",
                    color = Color(0xffdce3f0),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = detalle.nombreProducto,
                    color = Color(0xffbbcac0),
                    fontSize = 14.sp,
                    lineHeight = 19.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(estadoColor.copy(alpha = 0.18f))
                    .border(
                        width = 1.dp,
                        color = estadoColor,
                        shape = RoundedCornerShape(999.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = estadoTexto,
                    color = estadoColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        RiesgoPrestamoCard(
            riesgo = riesgo
        )

        Spacer(modifier = Modifier.height(18.dp))

        DetalleSeccionTitulo("Simulación guardada")

        DetalleDatoFila(
            titulo = "Monto solicitado",
            valor = "S/ ${"%,.2f".format(detalle.montoSolicitado)}"
        )

        DetalleDatoFila(
            titulo = "Plazo",
            valor = "${detalle.plazoMeses} meses"
        )

        DetalleDatoFila(
            titulo = "Score usado",
            valor = "${detalle.scoreUsado}"
        )

        DetalleDatoFila(
            titulo = "Nivel usado",
            valor = detalle.nivelRiesgoUsado
        )

        Spacer(modifier = Modifier.height(18.dp))

        DetalleSeccionTitulo("Costo estimado guardado")

        DetalleDatoFila(
            titulo = "TEA referencial",
            valor = "${detalle.teaUsadaPct}%"
        )

        DetalleDatoFila(
            titulo = "TEM calculada",
            valor = "%.4f%%".format(detalle.temCalculada * 100)
        )

        DetalleDatoFila(
            titulo = "Cuota base",
            valor = "S/ ${"%,.2f".format(detalle.cuotaBase)}"
        )

        DetalleDatoFila(
            titulo = "Seguro mensual",
            valor = "S/ ${"%,.2f".format(detalle.seguroMensual)}"
        )

        DetalleDatoFila(
            titulo = "Cuota estimada",
            valor = "S/ ${"%,.2f".format(detalle.cuotaEstimada)}"
        )

        DetalleDatoFila(
            titulo = "Costo total estimado",
            valor = "S/ ${"%,.2f".format(detalle.costoTotalEstimado)}"
        )

        DetalleDatoFila(
            titulo = "Interés + seguro total",
            valor = "S/ ${"%,.2f".format(detalle.interesYSeguroTotal)}"
        )

        DetalleDatoFila(
            titulo = "Ratio final",
            valor = "%.1f%%".format(detalle.ratioPostCredito * 100)
        )

        if (detalle.motivosExclusion.isNotBlank()) {
            Spacer(modifier = Modifier.height(18.dp))

            DetalleSeccionTitulo("Observaciones guardadas")

            detalle.motivosExclusion
                .split(";")
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .forEach { motivo ->
                    Text(
                        text = "• $motivo",
                        color = Color(0xffbbcac0),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
        }
    }
}