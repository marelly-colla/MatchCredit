package com.example.matchcredit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.matchcredit.data.repository.BancoRepository
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.ProductoCrediticioRepository
import com.example.matchcredit.domain.calculator.ResultadoPrestamoCalculado

@Composable
fun ResultadosPrestamoScreen(
    usuarioId: Int,
    tipoPrestamo: String,
    montoSolicitado: Double,
    plazoMeses: Int,
    perfilFinancieroRepository: PerfilFinancieroRepository,
    productoCrediticioRepository: ProductoCrediticioRepository,
    bancoRepository: BancoRepository,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = remember(usuarioId, tipoPrestamo, montoSolicitado, plazoMeses) {
        ResultadosPrestamoViewModel(
            usuarioId = usuarioId,
            tipoPrestamo = tipoPrestamo,
            montoSolicitado = montoSolicitado,
            plazoMeses = plazoMeses,
            perfilFinancieroRepository = perfilFinancieroRepository,
            productoCrediticioRepository = productoCrediticioRepository,
            bancoRepository = bancoRepository
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

            Text(
                text = "Resultados",
                color = Color(0xffdce3f0),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$tipoPrestamo por S/ ${"%,.2f".format(montoSolicitado)} a $plazoMeses meses",
                color = Color(0xffbbcac0),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            when {
                state.cargando -> {
                    MensajeResultadosCard(
                        titulo = "Calculando opciones",
                        texto = "Estamos evaluando bancos, requisitos y cuotas estimadas.",
                        esError = false
                    )
                }

                state.error != null -> {
                    MensajeResultadosCard(
                        titulo = "No se pudo calcular",
                        texto = state.error ?: "",
                        esError = true
                    )
                }

                else -> {
                    MensajeResultadosCard(
                        titulo = "Ranking generado",
                        texto = "Las opciones se ordenan priorizando productos recomendados y menor cuota estimada.",
                        esError = false
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    state.resultados.forEach { resultado ->
                        ResultadoProductoCard(resultado = resultado)
                        Spacer(modifier = Modifier.height(14.dp))
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
fun MensajeResultadosCard(
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

@Composable
fun ResultadoProductoCard(
    resultado: ResultadoPrestamoCalculado
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
                    text = "#${resultado.ranking ?: "-"} ${resultado.bancoNombre}",
                    color = Color(0xffdce3f0),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = resultado.producto.nombreProducto,
                    color = Color(0xffbbcac0),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
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

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MiniResultadoItem(
                titulo = "CUOTA EST.",
                valor = "S/ ${"%,.2f".format(resultado.paymentResult.cuotaEstimada)}",
                modifier = Modifier.weight(1f)
            )

            MiniResultadoItem(
                titulo = "COSTO TOTAL",
                valor = "S/ ${"%,.2f".format(resultado.paymentResult.costoTotalEstimado)}",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MiniResultadoItem(
                titulo = "TEA REF.",
                valor = "${resultado.paymentResult.teaUsadaPct}%",
                modifier = Modifier.weight(1f)
            )

            MiniResultadoItem(
                titulo = "RATIO FINAL",
                valor = "%.1f%%".format(resultado.ratioPostCredito * 100),
                modifier = Modifier.weight(1f)
            )
        }

        if (resultado.motivosExclusion.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Observaciones",
                color = Color(0xffbbcac0),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            resultado.motivosExclusion.forEach { motivo ->
                Text(
                    text = "• $motivo",
                    color = Color(0xff6b7280),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

@Composable
fun MiniResultadoItem(
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
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}