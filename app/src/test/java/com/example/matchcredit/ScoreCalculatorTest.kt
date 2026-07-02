package com.example.matchcredit

import com.example.matchcredit.domain.calculator.ScoreCalculator
import com.example.matchcredit.domain.enums.ClasificacionDeclarada
import com.example.matchcredit.domain.enums.NivelRiesgo
import com.example.matchcredit.domain.enums.TipoTrabajo
import org.junit.Assert.assertEquals
import org.junit.Test

class ScoreCalculatorTest {

    private val calculator = ScoreCalculator()

    @Test
    fun calcularScore_usuarioConBuenPerfil_retornaAltaCompatibilidad() {
        val resultado = calculator.calcularScore(
            ingresoMensual = 5500.0,
            gastosMensuales = 1200.0,
            cuotaMensualDeudas = 25.0,
            tipoTrabajo = TipoTrabajo.PLANILLA,
            antiguedadMeses = 36,
            clasificacionCrediticia = ClasificacionDeclarada.NORMAL,
            montoAhorros = 2500.0,
            edad = 28
        )

        assertEquals(95, resultado.score)
        assertEquals(NivelRiesgo.ALTA_COMPATIBILIDAD, resultado.nivelRiesgo)
        assertEquals(30, resultado.puntajeCapacidadPago)
        assertEquals(20, resultado.puntajeIngreso)
        assertEquals(10, resultado.puntajeTrabajo)
        assertEquals(10, resultado.puntajeAntiguedad)
        assertEquals(15, resultado.puntajeHistorial)
        assertEquals(5, resultado.puntajeAhorros)
        assertEquals(5, resultado.puntajeEdad)
    }

    @Test
    fun calcularScore_usuarioConPerfilRiesgoso_retornaRiesgoAlto() {
        val resultado = calculator.calcularScore(
            ingresoMensual = 1200.0,
            gastosMensuales = 1000.0,
            cuotaMensualDeudas = 500.0,
            tipoTrabajo = TipoTrabajo.INFORMAL,
            antiguedadMeses = 2,
            clasificacionCrediticia = ClasificacionDeclarada.PERDIDA,
            montoAhorros = 0.0,
            edad = 20
        )

        assertEquals(NivelRiesgo.RIESGO_ALTO, resultado.nivelRiesgo)
    }

    @Test
    fun calcularScore_edadFueraDeRango_noSumaPuntajeEdad() {
        val resultado = calculator.calcularScore(
            ingresoMensual = 3000.0,
            gastosMensuales = 800.0,
            cuotaMensualDeudas = 100.0,
            tipoTrabajo = TipoTrabajo.CONTRATO,
            antiguedadMeses = 12,
            clasificacionCrediticia = ClasificacionDeclarada.NORMAL,
            montoAhorros = 0.0,
            edad = 17
        )

        assertEquals(0, resultado.puntajeEdad)
    }
}