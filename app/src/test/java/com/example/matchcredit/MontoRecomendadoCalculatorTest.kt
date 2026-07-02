package com.example.matchcredit

import com.example.matchcredit.domain.calculator.MontoRecomendadoCalculator
import com.example.matchcredit.domain.calculator.NivelMontoRecomendado
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MontoRecomendadoCalculatorTest {

    @Test
    fun calcular_perfilCompletoPersonal_retornaMontoDisponible() {
        val resultado = MontoRecomendadoCalculator.calcular(
            ingresoMensual = 5500.0,
            gastosMensuales = 1200.0,
            cuotaMensualDeudas = 25.0,
            capacidadPagoDisponible = 4275.0,
            tipoPrestamo = "Personal",
            plazoMeses = 24
        )

        assertEquals(NivelMontoRecomendado.DISPONIBLE, resultado.nivel)
        assertTrue(resultado.cuotaMaximaSaludable > 0.0)
        assertTrue(resultado.montoMaximoEstimado > 0.0)
        assertEquals(24, resultado.plazoUsadoMeses)
        assertEquals(28.0, resultado.teaReferencialPct, 0.01)
    }

    @Test
    fun calcular_perfilSinIngreso_retornaIncompleto() {
        val resultado = MontoRecomendadoCalculator.calcular(
            ingresoMensual = null,
            gastosMensuales = null,
            cuotaMensualDeudas = null,
            capacidadPagoDisponible = null,
            tipoPrestamo = "Personal",
            plazoMeses = 24
        )

        assertEquals(NivelMontoRecomendado.INCOMPLETO, resultado.nivel)
        assertEquals(0.0, resultado.cuotaMaximaSaludable, 0.01)
        assertEquals(0.0, resultado.montoMaximoEstimado, 0.01)
    }

    @Test
    fun calcular_perfilSinCapacidad_retornaNoRecomendado() {
        val resultado = MontoRecomendadoCalculator.calcular(
            ingresoMensual = 2000.0,
            gastosMensuales = 1500.0,
            cuotaMensualDeudas = 700.0,
            capacidadPagoDisponible = -200.0,
            tipoPrestamo = "Personal",
            plazoMeses = 24
        )

        assertEquals(NivelMontoRecomendado.NO_RECOMENDADO, resultado.nivel)
        assertEquals(0.0, resultado.cuotaMaximaSaludable, 0.01)
        assertEquals(0.0, resultado.montoMaximoEstimado, 0.01)
    }

    @Test
    fun calcular_hipotecarioSinPlazo_usaPlazoYTeaReferencialHipotecaria() {
        val resultado = MontoRecomendadoCalculator.calcular(
            ingresoMensual = 7000.0,
            gastosMensuales = 1800.0,
            cuotaMensualDeudas = 300.0,
            capacidadPagoDisponible = 4900.0,
            tipoPrestamo = "Hipotecario",
            plazoMeses = null
        )

        assertEquals(120, resultado.plazoUsadoMeses)
        assertEquals(11.5, resultado.teaReferencialPct, 0.01)
        assertTrue(resultado.montoMaximoEstimado > 0.0)
    }

    @Test
    fun calcular_vehicularSinPlazo_usaPlazoYTeaReferencialVehicular() {
        val resultado = MontoRecomendadoCalculator.calcular(
            ingresoMensual = 6000.0,
            gastosMensuales = 1500.0,
            cuotaMensualDeudas = 300.0,
            capacidadPagoDisponible = 4200.0,
            tipoPrestamo = "Vehicular",
            plazoMeses = null
        )

        assertEquals(48, resultado.plazoUsadoMeses)
        assertEquals(18.0, resultado.teaReferencialPct, 0.01)
        assertTrue(resultado.montoMaximoEstimado > 0.0)
    }
}