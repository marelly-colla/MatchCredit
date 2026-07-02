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
import com.example.matchcredit.data.dto.ResultadoGuardadoResumen
import com.example.matchcredit.data.repository.ResultadoGuardadoRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistorialSimulacionesScreen(
    usuarioId: Int,
    resultadoGuardadoRepository: ResultadoGuardadoRepository,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = remember(usuarioId) {
        HistorialSimulacionesViewModel(
            usuarioId = usuarioId,
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
                .padding(bottom = 88.dp)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            TopBar()

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Mis simulaciones",
                color = Color(0xffdce3f0),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Revisa, vuelve a abrir o elimina los préstamos que guardaste anteriormente.",
                color = Color(0xffbbcac0),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            state.mensaje?.let { mensaje ->
                HistorialMensajeCard(
                    titulo = "Actualizado",
                    texto = mensaje,
                    esError = false
                )

                Spacer(modifier = Modifier.height(14.dp))
            }

            when {
                state.cargando -> {
                    CircularProgressIndicator(
                        color = Color(0xff5af0b3)
                    )
                }

                state.error != null -> {
                    HistorialMensajeCard(
                        titulo = "No se pudo cargar",
                        texto = state.error ?: "",
                        esError = true
                    )
                }

                state.simulaciones.isEmpty() -> {
                    HistorialMensajeCard(
                        titulo = "Sin simulaciones guardadas",
                        texto = "Cuando guardes una simulación desde el detalle de un préstamo, aparecerá aquí.",
                        esError = false
                    )
                }

                else -> {
                    state.simulaciones.forEach { simulacion ->
                        SimulacionGuardadaCard(
                            simulacion = simulacion,
                            eliminando = state.eliminandoId == simulacion.resultadoId,
                            onVerDetalle = {
                                navController.navigate(
                                    "detalleSimulacionGuardada/$usuarioId/${simulacion.resultadoId}"
                                )
                            },
                            onEliminar = {
                                viewModel.eliminarSimulacion(simulacion.resultadoId)
                            }
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                    }
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
fun SimulacionGuardadaCard(
    simulacion: ResultadoGuardadoResumen,
    eliminando: Boolean,
    onVerDetalle: () -> Unit,
    onEliminar: () -> Unit
) {
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = simulacion.nombreBanco,
                    color = Color(0xff5af0b3),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = simulacion.nombreProducto,
                    color = Color(0xffdce3f0),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(0xff0d141d))
                    .border(
                        width = 1.dp,
                        color = Color(0xff3c4a42),
                        shape = RoundedCornerShape(999.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "#${simulacion.ranking}",
                    color = Color(0xffbbcac0),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Guardado el ${formatearFechaSimulacion(simulacion.fechaSimulacion)}",
            color = Color(0xff6b7280),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HistorialMiniItem(
                titulo = "MONTO",
                valor = "S/ ${"%,.2f".format(simulacion.montoSolicitado)}",
                modifier = Modifier.weight(1f)
            )

            HistorialMiniItem(
                titulo = "PLAZO",
                valor = "${simulacion.plazoMeses} meses",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HistorialMiniItem(
                titulo = "CUOTA EST.",
                valor = "S/ ${"%,.2f".format(simulacion.cuotaEstimada)}",
                modifier = Modifier.weight(1f)
            )

            HistorialMiniItem(
                titulo = "COSTO TOTAL",
                valor = "S/ ${"%,.2f".format(simulacion.costoTotalEstimado)}",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HistorialMiniItem(
                titulo = "TEA REF.",
                valor = "${simulacion.teaUsadaPct}%",
                modifier = Modifier.weight(1f)
            )

            HistorialMiniItem(
                titulo = "SCORE",
                valor = "${simulacion.scoreUsado}",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Nivel usado: ${simulacion.nivelRiesgoUsado}",
            color = Color(0xffbbcac0),
            fontSize = 12.sp,
            lineHeight = 17.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(0xff5af0b3).copy(alpha = 0.16f))
                    .border(
                        width = 1.dp,
                        color = Color(0xff5af0b3),
                        shape = RoundedCornerShape(999.dp)
                    )
                    .clickable {
                        onVerDetalle()
                    }
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ver detalle",
                    color = Color(0xff5af0b3),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(0xffef5350).copy(alpha = 0.14f))
                    .border(
                        width = 1.dp,
                        color = Color(0xffef5350),
                        shape = RoundedCornerShape(999.dp)
                    )
                    .clickable(enabled = !eliminando) {
                        onEliminar()
                    }
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (eliminando) "Eliminando..." else "Eliminar",
                    color = Color(0xffef5350),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HistorialMiniItem(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xff0d141d))
            .border(
                width = 1.dp,
                color = Color(0xff3c4a42),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = titulo,
            color = Color(0xffbbcac0),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = valor,
            color = Color(0xffdce3f0),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HistorialMensajeCard(
    titulo: String,
    texto: String,
    esError: Boolean
) {
    val color = if (esError) Color(0xffef5350) else Color(0xff5af0b3)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xff151c25))
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = titulo,
            color = color,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = texto,
            color = Color(0xffbbcac0),
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}

fun formatearFechaSimulacion(
    fechaMillis: Long
): String {
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formato.format(Date(fechaMillis))
}