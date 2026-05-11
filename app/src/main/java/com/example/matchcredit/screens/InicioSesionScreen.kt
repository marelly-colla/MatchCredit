package com.example.matchcredit.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.matchcredit.R
import com.example.matchcredit.data.MatchCreditDatabase
import kotlinx.coroutines.launch

@Composable
fun InicioDeSesinMatchCredit(
    modifier: Modifier = Modifier,
    onNavigateToRegistro: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xff0d141d)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xff192029))
                    .border(1.dp, Color(0xff3c4a42), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.container),
                    contentDescription = "Logo",
                    colorFilter = ColorFilter.tint(Color(0xff5af0b3)),
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "MatchCredit",
                color = Color(0xff5af0b3),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.64).sp
                )
            )

            Text(
                text = "Tu libertad financiera comienza aquí.",
                color = Color(0xffbbcac0),
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xff192029))
                    .border(1.dp, Color(0xff3c4a42), RoundedCornerShape(12.dp))
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "CORREO ELECTRÓNICO",
                        color = Color(0xffbbcac0),
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.6.sp)
                    )
                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        placeholder = { Text("ejem@matchcredit.com", color = Color(0xff6b7280)) },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.icon_correo),
                                contentDescription = "Email Icon",
                                colorFilter = ColorFilter.tint(Color(0xffbbcac0)),
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color(0xff151c25),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            unfocusedBorderColor = Color(0xff3c4a42),
                            focusedBorderColor = Color(0xff5af0b3)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "CONTRASEÑA",
                        color = Color(0xffbbcac0),
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.6.sp)
                    )
                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        placeholder = { Text("••••••••", color = Color(0xff6b7280)) },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.icon_candado),
                                contentDescription = "Password Icon",
                                colorFilter = ColorFilter.tint(Color(0xffbbcac0)),
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color(0xff151c25),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            unfocusedBorderColor = Color(0xff3c4a42),
                            focusedBorderColor = Color(0xff5af0b3)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xff34d399))
                        .clickable {
                            if (correo.isNotEmpty() && contrasena.isNotEmpty()) {

                                coroutineScope.launch {
                                    try {
                                        val db = Room.databaseBuilder(
                                            context,
                                            MatchCreditDatabase::class.java, "matchcredit-db"
                                        ).build()

                                        val usuarioEncontrado = db.usuarioDao().obtenerUsuarioPorCorreo(correo)

                                        if (usuarioEncontrado == null) {
                                            Toast.makeText(context, "El usuario no existe", Toast.LENGTH_LONG).show()
                                        } else if (usuarioEncontrado.contrasena != contrasena) {
                                            Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
                                        } else {
                                            Toast.makeText(context, "Ingresando a la aplicación...", Toast.LENGTH_LONG).show()
                                        }

                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error al verificar: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                }

                            } else {
                                Toast.makeText(context, "Por favor, llena los campos", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Iniciar Sesión",
                            color = Color(0xff00563b),
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "›",
                            color = Color(0xff00563b),
                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "¿No tienes una cuenta?",
                color = Color(0xffbbcac0),
                style = TextStyle(fontSize = 14.sp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xff34d399))
                    .clickable {
                        onNavigateToRegistro()
                    }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Registrarme",
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
        }
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun InicioDeSesinMatchCreditPreview() {
    InicioDeSesinMatchCredit(onNavigateToRegistro = {})
}