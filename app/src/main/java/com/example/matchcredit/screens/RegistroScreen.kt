package com.example.matchcredit.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.matchcredit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroMatchCredit(modifier: Modifier = Modifier) {

    // ✅ VARIABLES DE TEXTO
    var nombres by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var ingreso by remember { mutableStateOf("") }
    var deudas by remember { mutableStateOf("") }

    // ✅ NUEVAS VARIABLES DE SELECCIÓN
    var estadoTrabajo by remember { mutableStateOf("Contratado") } // Guarda la opción seleccionada
    var aceptoTerminos by remember { mutableStateOf(false) } // Guarda si el check está marcado o no

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xff0d141d)) // Fondo oscuro global
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Activa el scroll
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 96.dp, bottom = 48.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(23.dp, Alignment.Top),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Título Regístrate
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Regístrate",
                                color = Color(0xffdce3f0),
                                textAlign = TextAlign.Center,
                                lineHeight = 1.25.em,
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = (-0.64).sp
                                ),
                                modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
                            )
                        }
                    }

                    // Contenedor principal de los formularios
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // TARJETA: Información personal
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(color = Color(0xff192029))
                                .border(
                                    border = BorderStroke(1.dp, Color(0xff3c4a42)),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(all = 24.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.container),
                                    contentDescription = "Container",
                                    colorFilter = ColorFilter.tint(Color(0xff5af0b3))
                                )
                                Column {
                                    Text(
                                        text = "Información personal",
                                        color = Color(0xffdce3f0),
                                        lineHeight = 1.33.em,
                                        style = TextStyle(
                                            fontSize = 24.sp,
                                            letterSpacing = (-0.24).sp
                                        ),
                                        modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
                                    )
                                }
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Campo Nombres
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "NOMBRES Y APELLIDOS",
                                        color = Color(0xffbbcac0),
                                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.6.sp),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = nombres,
                                        onValueChange = { nombres = it },
                                        placeholder = { Text("Ej. Johnathan Doe", color = Color(0xff6b7280)) },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color(0xff0d141d),
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                // Campo Correo
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "CORREO ELECTRÓNICO",
                                        color = Color(0xffbbcac0),
                                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.6.sp),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = correo,
                                        onValueChange = { correo = it },
                                        placeholder = { Text("john@matchcredit.com", color = Color(0xff6b7280)) },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color(0xff0d141d),
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                // Campo Contraseña
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "CONTRASEÑA",
                                        color = Color(0xffbbcac0),
                                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.6.sp),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = contrasena,
                                        onValueChange = { contrasena = it },
                                        placeholder = { Text("••••••••", color = Color(0xff6b7280)) },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color(0xff0d141d),
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                        // TARJETA: Finanzas
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(color = Color(0xff192029))
                                .border(
                                    border = BorderStroke(1.dp, Color(0xff3c4a42)),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(all = 24.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.container),
                                    contentDescription = "Container",
                                    colorFilter = ColorFilter.tint(Color(0xff5af0b3))
                                )
                                Column {
                                    Text(
                                        text = "Finanzas",
                                        color = Color(0xffdce3f0),
                                        lineHeight = 1.33.em,
                                        style = TextStyle(
                                            fontSize = 24.sp,
                                            letterSpacing = (-0.24).sp
                                        ),
                                        modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
                                    )
                                }
                            }

                            // Campo Ingreso Mensual
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "INGRESO MENSUAL",
                                    color = Color(0xffbbcac0),
                                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.6.sp),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = ingreso,
                                    onValueChange = { ingreso = it },
                                    placeholder = { Text("5,000", color = Color(0xff6b7280)) },
                                    leadingIcon = { Text("S/.", color = Color(0xffbbcac0)) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color(0xff0d141d),
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            // Campo Deudas
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "TOTAL EN DEUDAS",
                                    color = Color(0xffbbcac0),
                                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.6.sp),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = deudas,
                                    onValueChange = { deudas = it },
                                    placeholder = { Text("1,200", color = Color(0xff6b7280)) },
                                    leadingIcon = { Text("S/.", color = Color(0xffbbcac0)) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color(0xff0d141d),
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        // TARJETA: Trabajo
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(color = Color(0xff192029))
                                .border(
                                    border = BorderStroke(1.dp, Color(0xff3c4a42)),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(all = 24.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.container),
                                    contentDescription = "Container",
                                    colorFilter = ColorFilter.tint(Color(0xff5af0b3))
                                )
                                Column {
                                    Text(
                                        text = "Trabajo",
                                        color = Color(0xffdce3f0),
                                        style = TextStyle(fontSize = 24.sp, letterSpacing = (-0.24).sp)
                                    )
                                }
                            }
                            Text(
                                text = "Seleccione su estado actual:",
                                color = Color(0xffbbcac0),
                                style = TextStyle(fontSize = 14.sp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            // ✅ SECCIÓN DE BOTONES DE TRABAJO (AHORA SELECCIONABLES)
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Botón Desempleado
                                    val isDesempleado = estadoTrabajo == "Desempleado"
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(50))
                                            .clickable { estadoTrabajo = "Desempleado" } // Cambia el estado al tocar
                                            .background(if (isDesempleado) Color(0xff34d399) else Color(0xff0d141d))
                                            .border(1.dp, if (isDesempleado) Color.Transparent else Color(0xff3c4a42), RoundedCornerShape(50))
                                            .padding(vertical = 12.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Desempleado",
                                            color = if (isDesempleado) Color(0xff00563b) else Color(0xffbbcac0),
                                            fontSize = 14.sp,
                                            fontWeight = if (isDesempleado) FontWeight.Medium else FontWeight.Normal
                                        )
                                    }

                                    // Botón Independiente
                                    val isIndependiente = estadoTrabajo == "Independiente"
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(50))
                                            .clickable { estadoTrabajo = "Independiente" }
                                            .background(if (isIndependiente) Color(0xff34d399) else Color(0xff0d141d))
                                            .border(1.dp, if (isIndependiente) Color.Transparent else Color(0xff3c4a42), RoundedCornerShape(50))
                                            .padding(vertical = 12.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Independiente",
                                            color = if (isIndependiente) Color(0xff00563b) else Color(0xffbbcac0),
                                            fontSize = 14.sp,
                                            fontWeight = if (isIndependiente) FontWeight.Medium else FontWeight.Normal
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Botón Contratado
                                    val isContratado = estadoTrabajo == "Contratado"
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(50))
                                            .clickable { estadoTrabajo = "Contratado" }
                                            .background(if (isContratado) Color(0xff34d399) else Color(0xff0d141d))
                                            .border(1.dp, if (isContratado) Color.Transparent else Color(0xff3c4a42), RoundedCornerShape(50))
                                            .padding(vertical = 12.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Contratado",
                                            color = if (isContratado) Color(0xff00563b) else Color(0xffbbcac0),
                                            fontSize = 14.sp,
                                            fontWeight = if (isContratado) FontWeight.Medium else FontWeight.Normal
                                        )
                                    }

                                    // Botón Retirado
                                    val isRetirado = estadoTrabajo == "Retirado"
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(50))
                                            .clickable { estadoTrabajo = "Retirado" }
                                            .background(if (isRetirado) Color(0xff34d399) else Color(0xff0d141d))
                                            .border(1.dp, if (isRetirado) Color.Transparent else Color(0xff3c4a42), RoundedCornerShape(50))
                                            .padding(vertical = 12.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Retirado",
                                            color = if (isRetirado) Color(0xff00563b) else Color(0xffbbcac0),
                                            fontSize = 14.sp,
                                            fontWeight = if (isRetirado) FontWeight.Medium else FontWeight.Normal
                                        )
                                    }
                                }
                            }

                            // Aviso de datos
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .requiredWidth(26.dp)
                                        .requiredHeight(40.dp)
                                        .clip(RoundedCornerShape(9999.dp))
                                        .background(Color(0xff5af0b3).copy(alpha = 0.1f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.container),
                                        contentDescription = "Info",
                                        colorFilter = ColorFilter.tint(Color(0xff5af0b3))
                                    )
                                }
                                Text(
                                    text = "Sus datos financieros se utilizan únicamente para fines de cotejo.",
                                    color = Color(0xffbbcac0),
                                    lineHeight = 1.4.em,
                                    style = TextStyle(fontSize = 12.sp),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        // ✅ SECCIÓN FINAL: BOTÓN CREAR CUENTA Y TÉRMINOS
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Botón Verde
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xff34d399))
                                    .clickable { /* Acción para crear cuenta */ }
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Crear Cuenta",
                                        color = Color(0xff00563b),
                                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "→",
                                        color = Color(0xff00563b),
                                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    )
                                }
                            }

                            // Checkbox y texto de términos
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                                    .clickable { aceptoTerminos = !aceptoTerminos }, // Al tocar la fila completa cambia el check
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                // Casilla personalizada (Checkbox)
                                Box(
                                    modifier = Modifier
                                        .requiredWidth(20.dp)
                                        .requiredHeight(20.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .border(1.dp, Color(0xff3c4a42), RoundedCornerShape(4.dp))
                                        .background(if (aceptoTerminos) Color(0xff34d399) else Color.Transparent),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (aceptoTerminos) {
                                        Text("✓", color = Color(0xff00563b), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }

                                Text(
                                    text = "Acepto los Términos de Servicio y Políticas de\nPrivacidad",
                                    color = Color(0xffbbcac0),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 1.4.em,
                                    style = TextStyle(fontSize = 12.sp),
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Header MatchCredit
        TopAppBar(
            title = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.container),
                        contentDescription = "Logo",
                        colorFilter = ColorFilter.tint(Color(0xff5af0b3))
                    )
                    Text(
                        text = "MatchCredit",
                        color = Color(0xff5af0b3),
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    )
                }
            }
        )
    }
}

@Preview(widthDp = 412, heightDp = 1330)
@Composable
private fun RegistroMatchCreditPreview() {
    RegistroMatchCredit()
}