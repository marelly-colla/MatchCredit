package com.example.matchcredit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.domain.calculator.MontoRecomendado
import com.example.matchcredit.domain.calculator.MontoRecomendadoCalculator
import com.example.matchcredit.domain.calculator.NivelMontoRecomendado

@Composable
fun ConsultaPrestamoScreen(
    usuarioId: Int,
    perfilFinancieroRepository: PerfilFinancieroRepository,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = remember(usuarioId) {
        ConsultaPrestamoViewModel(
            usuarioId = usuarioId,
            perfilFinancieroRepository = perfilFinancieroRepository
        )
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var tipoPrestamo by rememberSaveable { mutableStateOf("Personal") }
    var montoSolicitado by rememberSaveable { mutableStateOf("") }
    var plazoMeses by rememberSaveable { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }
    var esMensajeError by remember { mutableStateOf(false) }

    val plazoParaSugerencia = plazoMeses.toIntOrNull()

    val montoRecomendado = MontoRecomendadoCalculator.calcular(
        ingresoMensual = state.perfil?.ingresoMensual,
        gastosMensuales = state.perfil?.gastosMensuales,
        cuotaMensualDeudas = state.perfil?.cuotaMensualDeudas,
        capacidadPagoDisponible = state.perfil?.capacidadPagoDisponible,
        tipoPrestamo = tipoPrestamo,
        plazoMeses = plazoParaSugerencia
    )

    val tiposPrestamo = listOf(
        "Personal" to "Para gastos personales, estudios, viajes o emergencias.",
        "Hipotecario" to "Para compra, construcción o mejora de vivienda.",
        "Vehicular" to "Para financiar la compra de un auto."
    )

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
                text = "Comparar préstamo",
                color = Color(0xffdce3f0),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Completa los datos de tu préstamo para estimar cuotas y comparar productos disponibles.",
                color = Color(0xffbbcac0),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            when {
                state.cargando -> {
                    OrientacionMontoCargandoCard()
                }

                state.error != null -> {
                    MensajeConsultaCard(
                        texto = state.error ?: "No se pudo cargar tu perfil financiero.",
                        esError = true
                    )
                }

                else -> {
                    OrientacionMontoCard(
                        montoRecomendado = montoRecomendado,
                        onUsarMonto = {
                            if (montoRecomendado.montoMaximoEstimado > 0.0) {
                                montoSolicitado = "%.0f".format(montoRecomendado.montoMaximoEstimado)
                                plazoMeses = montoRecomendado.plazoUsadoMeses.toString()
                                esMensajeError = false
                                mensaje = "Se usó el monto sugerido como referencia. Puedes ajustarlo antes de buscar opciones."
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

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
                Text(
                    text = "TIPO DE PRÉSTAMO",
                    color = Color(0xffbbcac0),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                tiposPrestamo.forEach { item ->
                    TipoPrestamoOptionCompact(
                        titulo = item.first,
                        descripcion = item.second,
                        seleccionado = tipoPrestamo == item.first,
                        onClick = {
                            tipoPrestamo = item.first
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))

                CampoConsultaPrestamo(
                    label = "MONTO SOLICITADO",
                    value = montoSolicitado,
                    placeholder = "Ejemplo: 5000",
                    onValueChange = { nuevoValor ->
                        montoSolicitado = nuevoValor.filter { it.isDigit() || it == '.' }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                CampoConsultaPrestamo(
                    label = "PLAZO EN MESES",
                    value = plazoMeses,
                    placeholder = "Ejemplo: 24",
                    onValueChange = { nuevoValor ->
                        plazoMeses = nuevoValor.filter { it.isDigit() }
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            mensaje?.let { texto ->
                MensajeConsultaCard(
                    texto = texto,
                    esError = esMensajeError
                )

                Spacer(modifier = Modifier.height(18.dp))
            }

            BuscarOpcionesButton(
                onClick = {
                    val monto = montoSolicitado.toDoubleOrNull()
                    val plazo = plazoMeses.toIntOrNull()

                    when {
                        monto == null || monto <= 0.0 -> {
                            esMensajeError = true
                            mensaje = "Ingresa un monto válido para continuar."
                        }

                        plazo == null || plazo <= 0 -> {
                            esMensajeError = true
                            mensaje = "Ingresa un plazo válido en meses."
                        }

                        else -> {
                            navController.navigate(
                                "resultadosPrestamo/$usuarioId/$tipoPrestamo/$monto/$plazo"
                            )
                        }
                    }
                }
            )

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
fun OrientacionMontoCargandoCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xff151c25))
            .border(
                width = 1.dp,
                color = Color(0xff3c4a42),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            color = Color(0xff5af0b3),
            modifier = Modifier.size(22.dp),
            strokeWidth = 2.dp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Calculando orientación de monto...",
            color = Color(0xffbbcac0),
            fontSize = 13.sp
        )
    }
}

@Composable
fun OrientacionMontoCard(
    montoRecomendado: MontoRecomendado,
    onUsarMonto: () -> Unit
) {
    val colorNivel = when (montoRecomendado.nivel) {
        NivelMontoRecomendado.DISPONIBLE -> Color(0xff5af0b3)
        NivelMontoRecomendado.LIMITADO -> Color(0xffffc107)
        NivelMontoRecomendado.NO_RECOMENDADO -> Color(0xffef5350)
        NivelMontoRecomendado.INCOMPLETO -> Color(0xff6b7280)
    }

    val etiquetaNivel = when (montoRecomendado.nivel) {
        NivelMontoRecomendado.DISPONIBLE -> "Disponible"
        NivelMontoRecomendado.LIMITADO -> "Limitado"
        NivelMontoRecomendado.NO_RECOMENDADO -> "Cuidado"
        NivelMontoRecomendado.INCOMPLETO -> "Pendiente"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xff151c25))
            .border(
                width = 1.dp,
                color = colorNivel,
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
                    text = "ORIENTACIÓN PREVIA",
                    color = Color(0xffbbcac0),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = montoRecomendado.titulo,
                    color = Color(0xffdce3f0),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )
            }

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
                    text = etiquetaNivel,
                    color = colorNivel,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = montoRecomendado.mensaje,
            color = Color(0xffbbcac0),
            fontSize = 13.sp,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OrientacionMiniItem(
                titulo = "CUOTA SALUDABLE",
                valor = "S/ ${"%,.0f".format(montoRecomendado.cuotaMaximaSaludable)}",
                modifier = Modifier.weight(1f)
            )

            OrientacionMiniItem(
                titulo = "MONTO ESTIMADO",
                valor = "S/ ${"%,.0f".format(montoRecomendado.montoMaximoEstimado)}",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Referencia calculada para ${montoRecomendado.plazoUsadoMeses} meses con TEA referencial de ${montoRecomendado.teaReferencialPct}%.",
            color = Color(0xff6b7280),
            fontSize = 12.sp,
            lineHeight = 17.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = montoRecomendado.recomendacion,
            color = colorNivel,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 18.sp
        )

        if (montoRecomendado.montoMaximoEstimado > 0.0) {
            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(colorNivel.copy(alpha = 0.16f))
                    .border(
                        width = 1.dp,
                        color = colorNivel,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable {
                        onUsarMonto()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Usar monto sugerido",
                    color = colorNivel,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun OrientacionMiniItem(
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

@Composable
fun TipoPrestamoOptionCompact(
    titulo: String,
    descripcion: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (seleccionado) Color(0xff5af0b3) else Color(0xff3c4a42)
    val textColor = if (seleccionado) Color(0xff5af0b3) else Color(0xffdce3f0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xff0d141d))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = titulo,
                color = textColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = descripcion,
                color = Color(0xff6b7280),
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }

        if (seleccionado) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(0xff5af0b3))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Activo",
                    color = Color(0xff0d141d),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CampoConsultaPrestamo(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            color = Color(0xffbbcac0),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xff6b7280),
                    fontSize = 14.sp
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xff0d141d),
                unfocusedContainerColor = Color(0xff0d141d),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xff5af0b3),
                unfocusedBorderColor = Color(0xff3c4a42),
                cursorColor = Color(0xff5af0b3)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(14.dp)
        )
    }
}

@Composable
fun MensajeConsultaCard(
    texto: String,
    esError: Boolean
) {
    val color = if (esError) Color(0xffef5350) else Color(0xff5af0b3)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xff151c25))
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(14.dp)
    ) {
        Text(
            text = if (esError) "Revisa los datos" else "Consulta preparada",
            color = color,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = texto,
            color = Color(0xffbbcac0),
            fontSize = 12.sp,
            lineHeight = 17.sp
        )
    }
}

@Composable
fun BuscarOpcionesButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xff5af0b3))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Buscar opciones",
            color = Color(0xff0d141d),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}