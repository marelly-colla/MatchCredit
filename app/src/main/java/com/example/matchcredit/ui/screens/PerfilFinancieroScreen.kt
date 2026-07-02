package com.example.matchcredit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.matchcredit.data.repository.UsuarioRepository
import com.example.matchcredit.domain.enums.ClasificacionDeclarada
import com.example.matchcredit.domain.enums.TipoTrabajo

@Composable
fun PerfilFinancieroScreen(
    usuarioId: Int,
    perfilFinancieroRepository: PerfilFinancieroRepository,
    usuarioRepository: UsuarioRepository,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = remember(usuarioId) {
        PerfilFinancieroViewModel(
            usuarioId = usuarioId,
            perfilFinancieroRepository = perfilFinancieroRepository,
            usuarioRepository = usuarioRepository
        )
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.guardadoExitoso) {
        if (state.guardadoExitoso) {
            navController.navigate("home/$usuarioId") {
                launchSingleTop = true
            }
        }
    }

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
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TopBar()

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tu perfil financiero",
                color = Color(0xffdce3f0),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Con esta información MatchCredit calcula tu score estimado y encuentra productos que se ajusten mejor a tu perfil.",
                color = Color(0xffbbcac0),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            PerfilFormCard(title = "Situación laboral") {
                PerfilEnumDropdown(
                    label = "Tipo de trabajo",
                    opciones = TipoTrabajo.entries,
                    seleccionado = state.tipoTrabajo,
                    texto = { it.descripcion },
                    onSeleccionar = viewModel::onTipoTrabajoChange
                )

                PerfilFormField(
                    label = "Antigüedad en el trabajo",
                    value = state.antiguedadTrabajandoMeses,
                    placeholder = "Ejemplo: 12",
                    helper = "Ingresa la cantidad en meses.",
                    onValueChange = viewModel::onAntiguedadChange,
                    keyboardType = KeyboardType.Number
                )
            }

            PerfilFormCard(title = "Ingresos y gastos") {
                PerfilFormField(
                    label = "Ingreso mensual neto",
                    value = state.ingresoMensual,
                    placeholder = "0.00",
                    prefix = "S/",
                    helper = "Monto que recibes al mes.",
                    onValueChange = viewModel::onIngresoChange,
                    keyboardType = KeyboardType.Decimal
                )

                PerfilFormField(
                    label = "Gastos mensuales",
                    value = state.gastosMensuales,
                    placeholder = "0.00",
                    prefix = "S/",
                    helper = "Incluye alimentación, transporte, servicios u otros gastos fijos.",
                    onValueChange = viewModel::onGastosChange,
                    keyboardType = KeyboardType.Decimal
                )
            }

            PerfilFormCard(title = "Deudas actuales") {
                PerfilFormField(
                    label = "Deuda total actual",
                    value = state.deudaTotalActual,
                    placeholder = "0.00",
                    prefix = "S/",
                    helper = "Monto total aproximado que debes actualmente.",
                    onValueChange = viewModel::onDeudaChange,
                    keyboardType = KeyboardType.Decimal
                )

                PerfilFormField(
                    label = "Cuota mensual de deudas",
                    value = state.cuotaMensualDeudas,
                    placeholder = "0.00",
                    prefix = "S/",
                    helper = "Cuánto pagas al mes por deudas actuales.",
                    onValueChange = viewModel::onCuotaChange,
                    keyboardType = KeyboardType.Decimal
                )
            }

            PerfilFormCard(title = "Historial y ahorros") {
                PerfilEnumDropdown(
                    label = "Clasificación declarada",
                    opciones = ClasificacionDeclarada.entries,
                    seleccionado = state.clasificacionSbs,
                    texto = { it.descripcion },
                    onSeleccionar = viewModel::onClasificacionChange
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xff0d141d))
                        .border(
                            width = 1.dp,
                            color = Color(0xff3c4a42),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "¿Tienes ahorros?",
                            color = Color(0xffdce3f0),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Este dato ayuda a estimar tu estabilidad financiera.",
                            color = Color(0xff6b7280),
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }

                    Switch(
                        checked = state.tieneAhorros,
                        onCheckedChange = viewModel::onTieneAhorrosChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xff5af0b3),
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color(0xff3c4a42)
                        )
                    )
                }

                if (state.tieneAhorros) {
                    PerfilFormField(
                        label = "Monto de ahorros",
                        value = state.montoAhorros,
                        placeholder = "0.00",
                        prefix = "S/",
                        helper = "Monto aproximado que tienes ahorrado.",
                        onValueChange = viewModel::onMontoAhorrosChange,
                        keyboardType = KeyboardType.Decimal
                    )
                }
            }

            if (state.error != null) {
                PerfilMensajeCard(
                    texto = state.error ?: "",
                    esError = true
                )
            }

            Button(
                onClick = viewModel::calcularYGuardar,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff5af0b3),
                    disabledContainerColor = Color(0xff3c4a42)
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color(0xff0d141d),
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Calcular y guardar perfil",
                        color = Color(0xff0d141d),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        BottomNavigationBar(
            selected = "profile",
            usuarioId = usuarioId,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PerfilFormCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
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
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = title.uppercase(),
            color = Color(0xffbbcac0),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PerfilEnumDropdown(
    label: String,
    opciones: List<T>,
    seleccionado: T,
    texto: (T) -> String,
    onSeleccionar: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label.uppercase(),
            color = Color(0xffbbcac0),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = texto(seleccionado),
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xff0d141d),
                    unfocusedContainerColor = Color(0xff0d141d),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedBorderColor = Color(0xff3c4a42),
                    focusedBorderColor = Color(0xff5af0b3),
                    cursorColor = Color(0xff5af0b3)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(14.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier.background(Color(0xff192029))
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = texto(opcion),
                                color = if (opcion == seleccionado) {
                                    Color(0xff5af0b3)
                                } else {
                                    Color(0xffdce3f0)
                                },
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            onSeleccionar(opcion)
                            expanded = false
                        },
                        modifier = Modifier.background(Color(0xff192029))
                    )
                }
            }
        }
    }
}

@Composable
fun PerfilFormField(
    label: String,
    value: String,
    placeholder: String,
    helper: String? = null,
    prefix: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label.uppercase(),
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
            leadingIcon = prefix?.let {
                {
                    Text(
                        text = it,
                        color = Color(0xffbbcac0),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xff0d141d),
                unfocusedContainerColor = Color(0xff0d141d),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedBorderColor = Color(0xff3c4a42),
                focusedBorderColor = Color(0xff5af0b3),
                cursorColor = Color(0xff5af0b3)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(14.dp)
        )

        if (helper != null) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = helper,
                color = Color(0xff6b7280),
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
fun PerfilMensajeCard(
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
            text = if (esError) "Revisa los datos" else "Perfil actualizado",
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