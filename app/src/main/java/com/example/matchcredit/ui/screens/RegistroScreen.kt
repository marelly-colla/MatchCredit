package com.example.matchcredit.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchcredit.R
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.ColumnScope
import androidx.navigation.NavHostController
import com.example.matchcredit.data.repository.UsuarioRepository

@Composable
fun RegistroMatchCredit(
    navController: NavHostController,
    usuarioRepository: UsuarioRepository,
    modifier: Modifier = Modifier
) {
    val viewModel = remember {
        RegistroViewModel(usuarioRepository)
    }

    var nombres by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var edad by remember {mutableStateOf("")}

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
                    FormField("EDAD", edad, "18") {edad = it}
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
                                        val usuarioId = viewModel.registrarUsuario(
                                            nombres = nombres,
                                            correo = correo,
                                            contrasena = contrasena,
                                            edad = edad.toInt()
                                        )
                                        navController.navigate("perfilFinanciero/$usuarioId"){
                                            popUpTo("registro") { inclusive = true }
                                        }
                                    }
                                }
                            }
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Continuar →", color = Color(0xff00563b), fontSize = 18.sp, fontWeight = FontWeight.Bold)
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