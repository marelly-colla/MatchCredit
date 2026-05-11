package com.example.matchcredit.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.matchcredit.R
import com.example.matchcredit.data.MatchCreditDatabase
import com.example.matchcredit.data.Usuario
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.ColumnScope

@Composable
fun RegistroMatchCredit(modifier: Modifier = Modifier) {
    var nombres by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var ingreso by remember { mutableStateOf("") }
    var deudas by remember { mutableStateOf("") }
    var estadoTrabajo by remember { mutableStateOf("Contratado") }
    var aceptoTerminos by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xff0d141d))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xff0d141d))
                    .padding(top = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.container),
                        contentDescription = "Logo",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color(0xff5af0b3))
                    )

                    Text(
                        text = "MatchCredit",
                        color = Color(0xff5af0b3),
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp
                        )
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 0.5.dp,
                    color = Color(0xffdce3f0).copy(alpha = 0.2f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Text(
                    text = "Regístrate",
                    color = Color(0xffdce3f0),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.64).sp
                    )
                )

                FormCard(title = "Información personal", iconTint = Color(0xff5af0b3)) {
                    FormField("NOMBRES Y APELLIDOS", nombres, "Ej. Johnathan Doe") { nombres = it }
                    FormField("CORREO ELECTRÓNICO", correo, "john@matchcredit.com") { correo = it }
                    FormField("CONTRASEÑA", contrasena, "••••••••", isPassword = true) { contrasena = it }
                }

                FormCard(title = "Finanzas", iconTint = Color(0xff5af0b3)) {
                    FormField("INGRESO MENSUAL", ingreso, "5,000", prefix = "S/.") { ingreso = it }
                    FormField("TOTAL EN DEUDAS", deudas, "1,200", prefix = "S/.") { deudas = it }
                }

                FormCard(title = "Trabajo", iconTint = Color(0xff5af0b3)) {
                    Text("Seleccione su estado actual:", color = Color(0xffbbcac0), fontSize = 14.sp)

                    WorkStatusGrid(estadoTrabajo) { estadoTrabajo = it }

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(26.dp).clip(RoundedCornerShape(99.dp)).background(Color(0xff5af0b3).copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(painter = painterResource(id = R.drawable.container), contentDescription = null, modifier = Modifier.size(14.dp), colorFilter = ColorFilter.tint(Color(0xff5af0b3)))
                        }
                        Text("Sus datos financieros se utilizan únicamente para fines de cotejo.", color = Color(0xffbbcac0), fontSize = 11.sp, lineHeight = 14.sp)
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.clickable { aceptoTerminos = !aceptoTerminos },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(20.dp).clip(RoundedCornerShape(4.dp)).border(1.dp, Color(0xff3c4a42), RoundedCornerShape(4.dp)).background(if (aceptoTerminos) Color(0xff34d399) else Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            if (aceptoTerminos) Text("✓", color = Color(0xff00563b), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Text("Acepto los Términos de Servicio y Políticas de Privacidad", color = Color(0xffbbcac0), fontSize = 12.sp, modifier = Modifier.padding(start = 12.dp), textAlign = TextAlign.Center)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xff34d399))
                            .clickable {
                                if (!aceptoTerminos) {
                                    Toast.makeText(context, "Debes aceptar los términos", Toast.LENGTH_SHORT).show()
                                } else {
                                    coroutineScope.launch {
                                        val db = Room.databaseBuilder(context, MatchCreditDatabase::class.java, "matchcredit-db").build()
                                        db.usuarioDao().insertarUsuario(Usuario(nombres = nombres, correo = correo, contrasena = contrasena, ingreso = ingreso, deudas = deudas, estadoTrabajo = estadoTrabajo))
                                        Toast.makeText(context, "¡Usuario guardado correctamente!", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Crear Cuenta →", color = Color(0xff00563b), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun FormCard(title: String, iconTint: Color, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xff192029))
            .border(1.dp, Color(0xff3c4a42), RoundedCornerShape(12.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Image(painter = painterResource(id = R.drawable.container), contentDescription = null, modifier = Modifier.size(20.dp), colorFilter = ColorFilter.tint(iconTint))
            Text(text = title, color = Color(0xffdce3f0), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
        content()
    }
}

@Composable
fun FormField(label: String, value: String, placeholder: String, isPassword: Boolean = false, prefix: String? = null, onValueChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, color = Color(0xffbbcac0), fontSize = 11.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xff6b7280), fontSize = 14.sp) },
            leadingIcon = prefix?.let { { Text(it, color = Color(0xffbbcac0), modifier = Modifier.padding(start = 8.dp)) } },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xff0d141d),
                unfocusedContainerColor = Color(0xff0d141d),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedBorderColor = Color(0xff3c4a42)
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun WorkStatusGrid(selected: String, onSelect: (String) -> Unit) {
    val options = listOf("Desempleado", "Independiente", "Contratado", "Retirado")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        for (i in 0 until 2) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                for (j in 0 until 2) {
                    val option = options[i * 2 + j]
                    val isSelected = selected == option
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) Color(0xff34d399) else Color(0xff0d141d))
                            .border(1.dp, if (isSelected) Color.Transparent else Color(0xff3c4a42), RoundedCornerShape(50))
                            .clickable { onSelect(option) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(option, color = if (isSelected) Color(0xff00563b) else Color(0xffbbcac0), fontSize = 13.sp)
                    }
                }
            }
        }
    }
}