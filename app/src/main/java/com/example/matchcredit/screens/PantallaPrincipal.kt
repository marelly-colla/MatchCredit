package com.example.matchcredit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.example.matchcredit.R


@Composable
fun PantallaPrincipalMatchCredit(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xff0d141d))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            TopBar()

            Spacer(modifier = Modifier.height(24.dp))

            BienvenidaSection()

            Spacer(modifier = Modifier.height(24.dp))

            CreditScoreCard()

            Spacer(modifier = Modifier.height(20.dp))

            EstadisticasSection()

            Spacer(modifier = Modifier.height(24.dp))

            GestionSection()
        }

        BottomNavigationBar(
            selected = "home",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TopBar() {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.container),
            contentDescription = "Logo",
            colorFilter = ColorFilter.tint(Color(0xff5af0b3)),
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "MatchCredit",
            color = Color(0xff5af0b3),
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun BienvenidaSection() {

    Column {

        Text(
            text = "BIENVENIDO DE NUEVO",
            color = Color(0xffbbcac0),
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Hola, Alex",
            color = Color(0xffdce3f0),
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun CreditScoreCard() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xff192029))
            .border(
                width = 1.dp,
                color = Color(0xff3c4a42),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(20.dp)
    ) {

        Text(
            text = "SALUD CREDITICIA",
            color = Color(0xffbbcac0),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "742",
            color = Color(0xff5af0b3),
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color(0xff2e353f))
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.74f)
                    .fillMaxHeight()
                    .background(Color(0xff5af0b3))
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Has subido 12 puntos este mes.",
            color = Color(0xffbbcac0),
            fontSize = 14.sp
        )
    }
}

@Composable
fun EstadisticasSection() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        EstadisticaCard(
            titulo = "INGRESOS",
            valor = "S/.42,500",
            subtitulo = "+5.2% vs anterior",
            modifier = Modifier.weight(1f)
        )

        EstadisticaCard(
            titulo = "AHORRO",
            valor = "S/.12,840",
            subtitulo = "Meta: 65%",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun EstadisticaCard(
    titulo: String,
    valor: String,
    subtitulo: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xff151c25))
            .border(
                1.dp,
                Color(0xff3c4a42),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {

        Text(
            text = titulo,
            color = Color(0xffbbcac0),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = valor,
            color = Color(0xffdce3f0),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitulo,
            color = Color(0xff5af0b3),
            fontSize = 12.sp
        )
    }
}

@Composable
fun GestionSection() {

    Column {

        Text(
            text = "Gestión",
            color = Color(0xffdce3f0),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            GestionItem("Historial")
            GestionItem("Estados")
            GestionItem("Ayuda")
            GestionItem("Ajustes")
        }
    }
}

@Composable
fun GestionItem(
    texto: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xff151c25))
            .border(
                1.dp,
                Color(0xff3c4a42),
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.container),
            contentDescription = texto,
            colorFilter = ColorFilter.tint(Color(0xffbbcac0)),
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = texto,
            color = Color(0xffdce3f0),
            fontSize = 12.sp
        )
    }
}

@Composable
fun BottomNavigationBar(
    selected: String,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xff192029))
            .padding(vertical = 16.dp),

        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        BottomItem(
            texto = "Principal",
            icon = R.drawable.ic_home,
            seleccionado = selected == "home"
        )

        BottomItem(
            texto = "Comparar",
            icon = R.drawable.ic_compare,
            seleccionado = selected == "compare"
        )

        BottomItem(
            texto = "Perfil",
            icon = R.drawable.ic_profile,
            seleccionado = selected == "profile"
        )
    }
}

@Composable
fun BottomItem(
    texto: String,
    icon: Int,
    seleccionado: Boolean
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = icon),
            contentDescription = texto,
            colorFilter = ColorFilter.tint(
                if (seleccionado)
                    Color(0xff68fcbf)
                else
                    Color(0xffbbcac0)
            ),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = texto,
            color = if (seleccionado)
                Color(0xff68fcbf)
            else
                Color(0xffbbcac0),
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {

    PantallaPrincipalMatchCredit()
}