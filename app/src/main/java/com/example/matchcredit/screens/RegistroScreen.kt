package com.example.matchcredit.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.example.matchcredit.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun RegistroMatchCredit(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 412.dp)
            .requiredHeight(height = 1330.dp)
            .background(color = Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .requiredWidth(width = 412.dp)
                .requiredHeight(height = 1330.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(height = 1300.dp)
                    .padding(start = 20.dp,
                        end = 20.dp,
                        top = 96.dp,
                        bottom = 48.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(23.dp, Alignment.Top),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Regístrate",
                                color = Color(0xffdce3f0),
                                textAlign = TextAlign.Center,
                                lineHeight = 1.25.em,
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = (-0.64).sp),
                                modifier = Modifier
                                    .wrapContentHeight(align = Alignment.CenterVertically))
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(color = Color(0xff192029))
                                .border(border = BorderStroke(1.dp, Color(0xff3c4a42)),
                                    shape = RoundedCornerShape(12.dp))
                                .padding(all = 24.dp)
                                .shadow(elevation = 2.dp,
                                    shape = RoundedCornerShape(12.dp))
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.container),
                                    contentDescription = "Container",
                                    colorFilter = ColorFilter.tint(Color(0xff5af0b3)))
                                Column() {
                                    Text(
                                        text = "Información personal",
                                        color = Color(0xffdce3f0),
                                        lineHeight = 1.33.em,
                                        style = TextStyle(
                                            fontSize = 24.sp,
                                            letterSpacing = (-0.24).sp),
                                        modifier = Modifier
                                            .wrapContentHeight(align = Alignment.CenterVertically))
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(height = 248.dp)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .requiredWidth(width = 322.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "NOMBRES Y APELLIDOS",
                                            color = Color(0xffbbcac0),
                                            lineHeight = 1.33.em,
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                letterSpacing = 0.6.sp),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(shape = RoundedCornerShape(8.dp))
                                            .background(color = Color(0xff0d141d))
                                            .border(border = BorderStroke(2.dp, Color(0xff3c4a42)),
                                                shape = RoundedCornerShape(8.dp))
                                            .padding(horizontal = 16.dp,
                                                vertical = 14.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Johnathan Doe",
                                                color = Color(0xff6b7280),
                                                style = TextStyle(
                                                    fontSize = 16.sp),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .requiredWidth(width = 322.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "CORREO ELECTRÓNICO",
                                            color = Color(0xffbbcac0),
                                            lineHeight = 1.33.em,
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                letterSpacing = 0.6.sp),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(shape = RoundedCornerShape(8.dp))
                                            .background(color = Color(0xff0d141d))
                                            .border(border = BorderStroke(2.dp, Color(0xff3c4a42)),
                                                shape = RoundedCornerShape(8.dp))
                                            .padding(horizontal = 16.dp,
                                                vertical = 14.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "john@matchcredit.com",
                                                color = Color(0xff6b7280),
                                                style = TextStyle(
                                                    fontSize = 16.sp),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .requiredWidth(width = 322.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "CONTRASEÑA",
                                            color = Color(0xffbbcac0),
                                            lineHeight = 1.33.em,
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                letterSpacing = 0.6.sp),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .requiredHeight(height = 51.dp)
                                    ) {
                                        OutlinedTextField(
                                            value = "",
                                            onValueChange = {},
                                            label = {
                                                Text(
                                                    text = "••••••••",
                                                    color = Color(0xff6b7280),
                                                    style = TextStyle(
                                                        fontSize = 16.sp),
                                                    modifier = Modifier
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                            },
                                            colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color(0xff0d141d)),
                                            modifier = Modifier
                                                .requiredWidth(width = 322.dp))
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffbbcac0)),
                                            modifier = Modifier
                                                .align(alignment = Alignment.TopEnd)
                                                .offset(x = (-12).dp,
                                                    y = 14.dp))
                                    }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeight(height = 581.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .requiredWidth(width = 372.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .background(color = Color(0xff192029))
                                    .border(border = BorderStroke(1.dp, Color(0xff3c4a42)),
                                        shape = RoundedCornerShape(12.dp))
                                    .padding(all = 24.dp)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xff5af0b3)))
                                        Column() {
                                            Text(
                                                text = "Finanzas",
                                                color = Color(0xffdce3f0),
                                                lineHeight = 1.33.em,
                                                style = TextStyle(
                                                    fontSize = 24.sp,
                                                    letterSpacing = (-0.24).sp),
                                                modifier = Modifier
                                                    .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = "INGRESO MENSUAL",
                                                    color = Color(0xffbbcac0),
                                                    lineHeight = 1.33.em,
                                                    style = TextStyle(
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        letterSpacing = 0.6.sp),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .requiredHeight(height = 52.dp)
                                            ) {
                                                OutlinedTextField(
                                                    value = "",
                                                    onValueChange = {},
                                                    label = {
                                                        Text(
                                                            text = "5,000",
                                                            color = Color(0xff6b7280),
                                                            style = TextStyle(
                                                                fontSize = 16.sp),
                                                            modifier = Modifier
                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                                    },
                                                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color(0xff0d141d)),
                                                    modifier = Modifier
                                                        .requiredWidth(width = 322.dp))
                                                Text(
                                                    text = "S/.",
                                                    color = Color(0xffbbcac0),
                                                    lineHeight = 1.5.em,
                                                    style = TextStyle(
                                                        fontSize = 16.sp),
                                                    modifier = Modifier
                                                        .align(alignment = Alignment.TopStart)
                                                        .offset(x = 12.dp,
                                                            y = 14.dp)
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                        }
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = "TOTAL EN DEUDAS",
                                                    color = Color(0xffbbcac0),
                                                    lineHeight = 1.33.em,
                                                    style = TextStyle(
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        letterSpacing = 0.6.sp),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .requiredHeight(height = 52.dp)
                                            ) {
                                                OutlinedTextField(
                                                    value = "",
                                                    onValueChange = {},
                                                    label = {
                                                        Text(
                                                            text = "1,200",
                                                            color = Color(0xff6b7280),
                                                            style = TextStyle(
                                                                fontSize = 16.sp),
                                                            modifier = Modifier
                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                                    },
                                                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color(0xff0d141d)),
                                                    modifier = Modifier
                                                        .requiredWidth(width = 322.dp))
                                                Text(
                                                    text = "S/.",
                                                    color = Color(0xffbbcac0),
                                                    lineHeight = 1.5.em,
                                                    style = TextStyle(
                                                        fontSize = 16.sp),
                                                    modifier = Modifier
                                                        .align(alignment = Alignment.TopStart)
                                                        .offset(x = 12.dp,
                                                            y = 14.dp)
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                        }
                                    }
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .requiredWidth(width = 372.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .background(color = Color(0xff192029))
                                    .border(border = BorderStroke(1.dp, Color(0xff3c4a42)),
                                        shape = RoundedCornerShape(12.dp))
                                    .padding(all = 24.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.container),
                                        contentDescription = "Container",
                                        colorFilter = ColorFilter.tint(Color(0xff5af0b3)))
                                    Column() {
                                        Text(
                                            text = "Trabajo",
                                            color = Color(0xffdce3f0),
                                            lineHeight = 1.33.em,
                                            style = TextStyle(
                                                fontSize = 24.sp,
                                                letterSpacing = (-0.24).sp),
                                            modifier = Modifier
                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Seleccione su estado actual:",
                                        color = Color(0xffbbcac0),
                                        lineHeight = 1.43.em,
                                        style = TextStyle(
                                            fontSize = 14.sp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight(align = Alignment.CenterVertically))
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeight(height = 92.dp)
                                ) {
                                    OutlinedTextField(
                                        value = "",
                                        onValueChange = {},
                                        label = {
                                            Text(
                                                text = "Desempleado",
                                                color = Color(0xffbbcac0),
                                                lineHeight = 1.43.em,
                                                style = TextStyle(
                                                    fontSize = 14.sp),
                                                modifier = Modifier
                                                    .wrapContentHeight(align = Alignment.CenterVertically))
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color(0xff0d141d)),
                                        modifier = Modifier
                                            .fillMaxHeight())
                                    OutlinedTextField(
                                        value = "",
                                        onValueChange = {},
                                        label = {
                                            Text(
                                                text = "Independiente",
                                                color = Color(0xffbbcac0),
                                                lineHeight = 1.43.em,
                                                style = TextStyle(
                                                    fontSize = 14.sp),
                                                modifier = Modifier
                                                    .wrapContentHeight(align = Alignment.CenterVertically))
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color(0xff0d141d)),
                                        modifier = Modifier
                                            .align(alignment = Alignment.TopStart)
                                            .offset(x = 138.09.dp,
                                                y = 0.dp)
                                            .fillMaxHeight())
                                    OutlinedTextField(
                                        value = "",
                                        onValueChange = {},
                                        label = {
                                            Text(
                                                text = "Contratado",
                                                color = Color(0xff00563b),
                                                lineHeight = 1.43.em,
                                                style = TextStyle(
                                                    fontSize = 14.sp),
                                                modifier = Modifier
                                                    .wrapContentHeight(align = Alignment.CenterVertically))
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color(0xff34d399)),
                                        modifier = Modifier
                                            .fillMaxHeight())
                                    OutlinedTextField(
                                        value = "",
                                        onValueChange = {},
                                        label = {
                                            Text(
                                                text = "Retirado",
                                                color = Color(0xffbbcac0),
                                                lineHeight = 1.43.em,
                                                style = TextStyle(
                                                    fontSize = 14.sp),
                                                modifier = Modifier
                                                    .wrapContentHeight(align = Alignment.CenterVertically))
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color(0xff0d141d)),
                                        modifier = Modifier
                                            .align(alignment = Alignment.TopStart)
                                            .offset(x = 131.86.dp,
                                                y = 0.dp)
                                            .fillMaxHeight())
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 24.dp)
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .requiredWidth(width = 26.dp)
                                                .requiredHeight(height = 40.dp)
                                                .clip(shape = RoundedCornerShape(9999.dp))
                                                .background(color = Color(0xff5af0b3).copy(alpha = 0.1f))
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.container),
                                                contentDescription = "Container",
                                                colorFilter = ColorFilter.tint(Color(0xff5af0b3)))
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(end = 26.809999465942383.dp)
                                        ) {
                                            Text(
                                                text = "Sus datos financieros se utilizan únicamente para fines de cotejo.",
                                                color = Color(0xffbbcac0),
                                                lineHeight = 1.63.em,
                                                style = TextStyle(
                                                    fontSize = 12.sp),
                                                modifier = Modifier
                                                    .requiredWidth(width = 237.dp)
                                                    .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                }
                            }
                        }
                        Scaffold(
                            topBar = {
                                CenterAlignedTopAppBar(
                                    title = {
                                        CenterAlignedTopAppBar(
                                            title = {
                                                Text(
                                                    text = "Crear Cuenta",
                                                    color = Color(0xff00563b),
                                                    textAlign = TextAlign.Center,
                                                    lineHeight = 1.33.em,
                                                    style = TextStyle(
                                                        fontSize = 24.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        letterSpacing = (-0.24).sp),
                                                    modifier = Modifier
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                            },
                                            actions = {
                                                Image(
                                                    painter = painterResource(id = R.drawable.container),
                                                    contentDescription = "Container",
                                                    colorFilter = ColorFilter.tint(Color(0xff00563b)))
                                            },
                                            modifier = Modifier
                                                .shadow(elevation = 6.dp))
                                    },
                                    navigationIcon = {
                                        Box(
                                            modifier = Modifier
                                                .clip(shape = RoundedCornerShape(12.dp))
                                                .background(color = Color.White)
                                                .shadow(elevation = 6.dp,
                                                    shape = RoundedCornerShape(12.dp)))
                                    },
                                    modifier = Modifier
                                        .shadow(elevation = 6.dp))
                            }
                        ) {
                                innerPadding ->
                            val contentModifier = Modifier.padding(innerPadding)
                            TextField(
                                value = "",
                                onValueChange = {},
                                label = {
                                    Text(
                                        text = "Acepto los Términos de Servicio y Políticas de Privacidad",
                                        color = Color(0xffbbcac0),
                                        textAlign = TextAlign.Center,
                                        lineHeight = 1.43.em,
                                        style = TextStyle(
                                            fontSize = 14.sp),
                                        modifier = Modifier
                                            .wrapContentHeight(align = Alignment.CenterVertically))
                                })

                        }
                    }
                }
            }
        }
        TopAppBar(
            title = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.container),
                        contentDescription = "Container",
                        colorFilter = ColorFilter.tint(Color(0xff5af0b3)))
                    Column() {
                        Text(
                            text = "MatchCredit",
                            color = Color(0xff5af0b3),
                            lineHeight = 1.25.em,
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.64).sp),
                            modifier = Modifier
                                .wrapContentHeight(align = Alignment.CenterVertically))
                    }
                }
            })
    }
}

@Preview(widthDp = 412, heightDp = 1330)
@Composable
private fun RegistroMatchCreditPreview() {
    RegistroMatchCredit(Modifier)
}