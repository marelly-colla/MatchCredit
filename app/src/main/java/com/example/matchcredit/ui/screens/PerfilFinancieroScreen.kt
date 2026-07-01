package com.example.matchcredit.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.UsuarioRepository
import com.example.matchcredit.domain.enums.ClasificacionDeclarada
import com.example.matchcredit.domain.enums.TipoTrabajo

@Composable
fun PerfilFinancieroScreen(
    usuarioId: Int,
    perfilFinancieroRepository: PerfilFinancieroRepository,
    usuarioRepository: UsuarioRepository,
    onGuardadoExitoso: () -> Unit
) {
    val viewModel = remember {
        PerfilFinancieroViewModel(
            usuarioId = usuarioId,
            perfilFinancieroRepository = perfilFinancieroRepository,
            usuarioRepository = usuarioRepository
        )
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(state.guardadoExitoso) {
        if (state.guardadoExitoso) onGuardadoExitoso()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff0d141d))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        Text(
            text = "Tu perfil financiero",
            color = Color(0xffdce3f0),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Con esta información encontramos los productos que mejor se ajustan a ti.",
            color = Color(0xff6b7280),
            fontSize = 13.sp
        )

        // Sección 1 — Situación laboral
        FormCard(title = "Situación laboral", iconTint = Color(0xff4caf82)) {
            EnumDropdown(
                label = "Tipo de trabajo",
                opciones = TipoTrabajo.entries,
                seleccionado = state.tipoTrabajo,
                texto = { it.descripcion },
                onSeleccionar = viewModel::onTipoTrabajoChange
            )
            FormField(
                label = "Antigüedad en el trabajo (meses)",
                value = state.antiguedadTrabajandoMeses,
                placeholder = "Ej: 12",
                onValueChange = viewModel::onAntiguedadChange,
                keyboardType = KeyboardType.Number
            )
        }

        // Sección 2 — Ingresos y gastos
        FormCard(title = "Ingresos y gastos", iconTint = Color(0xff4caf82)) {
            FormField(
                label = "Ingreso mensual neto",
                value = state.ingresoMensual,
                placeholder = "0.00",
                prefix = "S/",
                onValueChange = viewModel::onIngresoChange,
                keyboardType = KeyboardType.Decimal
            )
            FormField(
                label = "Gastos mensuales",
                value = state.gastosMensuales,
                placeholder = "0.00",
                prefix = "S/",
                onValueChange = viewModel::onGastosChange,
                keyboardType = KeyboardType.Decimal
            )
        }

        // Sección 3 — Deudas actuales
        FormCard(title = "Deudas actuales", iconTint = Color(0xff4caf82)) {
            FormField(
                label = "Deuda total actual",
                value = state.deudaTotalActual,
                placeholder = "0.00",
                prefix = "S/",
                onValueChange = viewModel::onDeudaChange,
                keyboardType = KeyboardType.Decimal
            )
            FormField(
                label = "Cuota mensual de deudas",
                value = state.cuotaMensualDeudas,
                placeholder = "0.00",
                prefix = "S/",
                onValueChange = viewModel::onCuotaChange,
                keyboardType = KeyboardType.Decimal
            )
        }

        // Sección 4 — Historial y ahorros
        FormCard(title = "Historial y ahorros", iconTint = Color(0xff4caf82)) {
            EnumDropdown(
                label = "Clasificación SBS declarada",
                opciones = ClasificacionDeclarada.entries,
                seleccionado = state.clasificacionSbs,
                texto = { it.descripcion },
                onSeleccionar = viewModel::onClasificacionChange
            )

            // Toggle ahorros
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "¿Tienes ahorros?", color = Color(0xffbbcac0), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                Switch(
                    checked = state.tieneAhorros,
                    onCheckedChange = viewModel::onTieneAhorrosChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xff4caf82),
                        uncheckedTrackColor = Color(0xff3c4a42)
                    )
                )
            }

            if (state.tieneAhorros) {
                FormField(
                    label = "Monto de ahorros",
                    value = state.montoAhorros,
                    placeholder = "0.00",
                    prefix = "S/",
                    onValueChange = viewModel::onMontoAhorrosChange,
                    keyboardType = KeyboardType.Decimal
                )
            }
        }

        // Error
        if (state.error != null) {
            Text(
                text = state.error!!,
                color = Color(0xffef5350),
                fontSize = 13.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xff2a1515))
                    .padding(12.dp)
            )
        }

        // Botón
        Button(
            onClick = viewModel::calcularYGuardar,
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff4caf82),
                disabledContainerColor = Color(0xff3c4a42)
            )
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Calcular y guardar perfil",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Dropdown genérico para enums
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> EnumDropdown(
    label: String,
    opciones: List<T>,
    seleccionado: T,
    texto: (T) -> String,
    onSeleccionar: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, color = Color(0xffbbcac0), fontSize = 11.sp, fontWeight = FontWeight.Medium)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = texto(seleccionado),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xff0d141d),
                    unfocusedContainerColor = Color(0xff0d141d),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedBorderColor = Color(0xff3c4a42),
                    focusedBorderColor = Color(0xff4caf82)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(8.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color(0xff192029))
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = texto(opcion),
                                color = if (opcion == seleccionado) Color(0xff4caf82) else Color(0xffdce3f0),
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

// FormField extendido con KeyboardType
@Composable
fun FormField(
    label: String,
    value: String,
    placeholder: String,
    isPassword: Boolean = false,
    prefix: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, color = Color(0xffbbcac0), fontSize = 11.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xff6b7280), fontSize = 14.sp) },
            leadingIcon = prefix?.let { { Text(it, color = Color(0xffbbcac0), modifier = Modifier.padding(start = 8.dp)) } },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xff0d141d),
                unfocusedContainerColor = Color(0xff0d141d),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedBorderColor = Color(0xff3c4a42),
                focusedBorderColor = Color(0xff4caf82)
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )
    }
}