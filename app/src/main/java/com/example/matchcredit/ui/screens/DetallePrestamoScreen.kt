package com.example.matchcredit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.matchcredit.data.repository.BancoRepository
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.ProductoCrediticioRepository
import com.example.matchcredit.data.repository.ResultadoGuardadoRepository
import com.example.matchcredit.domain.calculator.ResultadoPrestamoCalculado

@Composable
fun DetallePrestamoScreen(
    usuarioId: Int,
    productoId: Int,
    montoSolicitado: Double,
    plazoMeses: Int,
    ranking: Int,
    perfilFinancieroRepository: PerfilFinancieroRepository,
    productoCrediticioRepository: ProductoCrediticioRepository,
    bancoRepository: BancoRepository,
    resultadoGuardadoRepository: ResultadoGuardadoRepository,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = remember(
        usuarioId,
        productoId,
        montoSolicitado,
        plazoMeses,
        ranking
    ) {
        DetallePrestamoViewModel(
            usuarioId = usuarioId,
            productoId = productoId,
            montoSolicitado = montoSolicitado,
            plazoMeses = plazoMeses,
            ranking = ranking,
            perfilFinancieroRepository = perfilFinancieroRepository,
            productoCrediticioRepository = productoCrediticioRepository,
            bancoRepository = bancoRepository,
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

            DetalleHeaderConFlecha(
                onBackClick = {
                    navController.popBackStack()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Revisa el costo estimado, requisitos y observaciones antes de guardar la simulación.",
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

                state.resultado != null -> {
                    DetalleContenido(
                        resultado = state.resultado!!,
                        montoSolicitado = montoSolicitado,
                        plazoMeses = plazoMeses
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    state.mensaje?.let { mensaje ->
                        DetalleMensajeCard(
                            titulo = if (state.guardadoExitoso) {
                                "Guardado"
                            } else {
                                "Aviso"
                            },
                            texto = mensaje,
                            esError = !state.guardadoExitoso
                        )

                        Spacer(modifier = Modifier.height(18.dp))
                    }

                    Button(
                        onClick = viewModel::guardarSimulacion,
                        enabled = !state.guardando && !state.guardadoExitoso,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff5af0b3),
                            disabledContainerColor = Color(0xff3c4a42)
                        )
                    ) {
                        when {
                            state.guardando -> {
                                CircularProgressIndicator(
                                    color = Color(0xff0d141d),
                                    modifier = Modifier.size(22.dp),
                                    strokeWidth = 2.dp
                                )
                            }

                            state.guardadoExitoso -> {
                                Text(
                                    text = "Simulación guardada",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            else -> {
                                Text(
                                    text = "Guardar simulación",
                                    color = Color(0xff0d141d),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        BottomNavigationBar(
            selected = "compare",
            usuarioId = usuarioId,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun DetalleHeaderConFlecha(
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
            text = "Detalle del préstamo",
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
fun DetalleContenido(
    resultado: ResultadoPrestamoCalculado,
    montoSolicitado: Double,
    plazoMeses: Int
) {
    val estadoColor = when {
        resultado.esRecomendado -> Color(0xff5af0b3)
        resultado.cumpleFiltros -> Color(0xffffc107)
        else -> Color(0xffef5350)
    }

    val estadoTexto = when {
        resultado.esRecomendado -> "Recomendado"
        resultado.cumpleFiltros -> "Revisar capacidad"
        else -> "No cumple"
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
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "#${resultado.ranking ?: "-"} ${resultado.bancoNombre}",
                    color = Color(0xffdce3f0),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = resultado.producto.nombreProducto,
                    color = Color(0xffbbcac0),
                    fontSize = 14.sp,
                    lineHeight = 19.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(estadoColor)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = estadoTexto,
                    color = Color(0xff0d141d),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        DetalleSeccionTitulo("Simulación solicitada")

        DetalleDatoFila(
            titulo = "Monto solicitado",
            valor = "S/ ${"%,.2f".format(montoSolicitado)}"
        )

        DetalleDatoFila(
            titulo = "Plazo",
            valor = "$plazoMeses meses"
        )

        DetalleDatoFila(
            titulo = "TEA referencial",
            valor = "${resultado.paymentResult.teaUsadaPct}%"
        )

        DetalleDatoFila(
            titulo = "TEM calculada",
            valor = "%.4f%%".format(resultado.paymentResult.temCalculada * 100)
        )

        Spacer(modifier = Modifier.height(18.dp))

        DetalleSeccionTitulo("Costo estimado")

        DetalleDatoFila(
            titulo = "Cuota base",
            valor = "S/ ${"%,.2f".format(resultado.paymentResult.cuotaBase)}"
        )

        DetalleDatoFila(
            titulo = "Seguro mensual",
            valor = "S/ ${"%,.2f".format(resultado.paymentResult.seguroMensual)}"
        )

        DetalleDatoFila(
            titulo = "Cuota estimada",
            valor = "S/ ${"%,.2f".format(resultado.paymentResult.cuotaEstimada)}"
        )

        DetalleDatoFila(
            titulo = "Costo total estimado",
            valor = "S/ ${"%,.2f".format(resultado.paymentResult.costoTotalEstimado)}"
        )

        DetalleDatoFila(
            titulo = "Interés + seguro total",
            valor = "S/ ${"%,.2f".format(resultado.paymentResult.interesYSeguroTotal)}"
        )

        DetalleDatoFila(
            titulo = "Ratio final",
            valor = "%.1f%%".format(resultado.ratioPostCredito * 100)
        )

        Spacer(modifier = Modifier.height(18.dp))

        DetalleSeccionTitulo("Requisitos del producto")

        DetalleDatoFila(
            titulo = "Monto mínimo",
            valor = "S/ ${"%,.2f".format(resultado.producto.montoMin)}"
        )

        DetalleDatoFila(
            titulo = "Monto máximo",
            valor = "S/ ${"%,.2f".format(resultado.producto.montoMax)}"
        )

        DetalleDatoFila(
            titulo = "Plazo mínimo",
            valor = "${resultado.producto.plazoMinMeses} meses"
        )

        DetalleDatoFila(
            titulo = "Plazo máximo",
            valor = "${resultado.producto.plazoMaxMeses} meses"
        )

        DetalleDatoFila(
            titulo = "Ingreso mínimo",
            valor = "S/ ${"%,.2f".format(resultado.producto.ingresoMin)}"
        )

        DetalleDatoFila(
            titulo = "Trabajo mínimo",
            valor = "${resultado.producto.minTrabajoMeses} meses"
        )

        if (resultado.producto.requisitosTexto.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = resultado.producto.requisitosTexto,
                color = Color(0xffbbcac0),
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }

        if (resultado.motivosExclusion.isNotEmpty()) {
            Spacer(modifier = Modifier.height(18.dp))

            DetalleSeccionTitulo("Observaciones")

            resultado.motivosExclusion.forEach { motivo ->
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

@Composable
fun DetalleSeccionTitulo(
    texto: String
) {
    Text(
        text = texto.uppercase(),
        color = Color(0xff5af0b3),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun DetalleDatoFila(
    titulo: String,
    valor: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titulo,
            color = Color(0xff6b7280),
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = valor,
            color = Color(0xffdce3f0),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DetalleMensajeCard(
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