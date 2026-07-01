package com.example.matchcredit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.example.matchcredit.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.UsuarioRepository
import com.example.matchcredit.domain.enums.NivelRiesgo

@Composable
fun PantallaPrincipalMatchCredit(
    usuarioId: Int,
    usuarioRepository: UsuarioRepository,
    perfilFinancieroRepository: PerfilFinancieroRepository,
    modifier: Modifier = Modifier
) {
    val viewModel = remember {
        HomeViewModel(
            usuarioId = usuarioId,
            usuarioRepository = usuarioRepository,
            perfilFinancieroRepository = perfilFinancieroRepository
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
                .padding(bottom = 72.dp) // espacio para el bottom bar
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            TopBar()
            Spacer(modifier = Modifier.height(24.dp))

            // Bienvenida con nombre real
            BienvenidaSection(
                nombre = state.usuario?.nombres?.split(" ")?.firstOrNull() ?: "...",
                edad = state.usuario?.edad
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Score con color según nivel de riesgo
            CreditScoreCard(
                score = state.perfil?.scoreMatchcredit,
                nivelRiesgo = state.perfil?.nivelRiesgo
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Estadísticas financieras reales
            EstadisticasSection(
                ingreso = state.perfil?.ingresoMensual,
                capacidadPago = state.perfil?.capacidadPagoDisponible
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Deuda y ratio
            DeudaSection(
                cuota = state.perfil?.cuotaMensualDeudas,
                ratio = state.perfil?.ratioEndeudamientoActual
            )

            Spacer(modifier = Modifier.height(24.dp))
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
fun BienvenidaSection(nombre: String, edad: Int?) {
    Column {
        Text(
            text = "BIENVENIDO DE NUEVO",
            color = Color(0xffbbcac0),
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Hola, $nombre",
            color = Color(0xffdce3f0),
            style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold)
        )
        if (edad != null) {
            Text(
                text = "$edad años",
                color = Color(0xff6b7280),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun CreditScoreCard(score: Int?, nivelRiesgo: NivelRiesgo?) {
    val colorScore = when (nivelRiesgo) {
        NivelRiesgo.ALTA_COMPATIBILIDAD -> Color(0xff5af0b3)
        NivelRiesgo.COMPATIBILIDAD_MEDIA -> Color(0xffa8e063)
        NivelRiesgo.COMPATIBILIDAD_BAJA -> Color(0xffffc107)
        NivelRiesgo.RIESGO_ALTO -> Color(0xffef5350)
        null -> Color(0xff3c4a42)
    }

    val labelNivel = nivelRiesgo?.categoria ?: "Sin perfil financiero"
    val progreso = (score ?: 0) / 100f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xff192029))
            .border(1.dp, Color(0xff3c4a42), RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Text(text = "SALUD CREDITICIA", color = Color(0xffbbcac0), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = score?.toString() ?: "--",
            color = colorScore,
            style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = labelNivel, color = colorScore, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(12.dp))

        // Barra de progreso
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color(0xff2e353f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progreso)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(999.dp))
                    .background(colorScore)
            )
        }

        if (nivelRiesgo != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = nivelRiesgo.interpretacion,
                color = Color(0xff6b7280),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun EstadisticasSection(ingreso: Double?, capacidadPago: Double?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EstadisticaCard(
            titulo = "INGRESO MENSUAL",
            valor = ingreso?.let { "S/ %,.0f".format(it) } ?: "--",
            modifier = Modifier.weight(1f)
        )
        EstadisticaCard(
            titulo = "CAPACIDAD DE PAGO",
            valor = capacidadPago?.let { "S/ %,.0f".format(it) } ?: "--",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DeudaSection(cuota: Double?, ratio: Double?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EstadisticaCard(
            titulo = "CUOTA MENSUAL",
            valor = cuota?.let { "S/ %,.0f".format(it) } ?: "--",
            modifier = Modifier.weight(1f)
        )
        EstadisticaCard(
            titulo = "RATIO DEUDA",
            valor = ratio?.let { "%.1f%%".format(it * 100) } ?: "--",
            modifier = Modifier.weight(1f)
        )
    }
}
@Composable
fun EstadisticaCard(
    titulo: String,
    valor: String,
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
