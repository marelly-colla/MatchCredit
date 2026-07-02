package com.example.matchcredit

import com.example.matchcredit.domain.calculator.DiagnosticoFinancieroCalculator
import com.example.matchcredit.domain.calculator.NivelDiagnosticoFinanciero
import org.junit.Assert.assertEquals
import org.junit.Test

class DiagnosticoFinancieroCalculatorTest {

    @Test
    fun calcular_perfilSaludable_retornaNivelSaludable() {
        val resultado = DiagnosticoFinancieroCalculator.calcular(
            score = 90,
            ratioEndeudamientoActual = 0.223,
            capacidadPagoDisponible = 4275.0,
            ingresoMensual = 5500.0
        )

        assertEquals(NivelDiagnosticoFinanciero.SALUDABLE, resultado.nivel)
        assertEquals("Perfil financiero saludable", resultado.titulo)
    }

    @Test
    fun calcular_perfilModerado_retornaNivelModerado() {
        val resultado = DiagnosticoFinancieroCalculator.calcular(
            score = 70,
            ratioEndeudamientoActual = 0.45,
            capacidadPagoDisponible = 1200.0,
            ingresoMensual = 3500.0
        )

        assertEquals(NivelDiagnosticoFinanciero.MODERADO, resultado.nivel)
    }

    @Test
    fun calcular_perfilRiesgoso_retornaNivelRiesgoso() {
        val resultado = DiagnosticoFinancieroCalculator.calcular(
            score = 40,
            ratioEndeudamientoActual = 0.70,
            capacidadPagoDisponible = 100.0,
            ingresoMensual = 1500.0
        )

        assertEquals(NivelDiagnosticoFinanciero.RIESGOSO, resultado.nivel)
    }

    @Test
    fun calcular_perfilIncompleto_retornaNivelIncompleto() {
        val resultado = DiagnosticoFinancieroCalculator.calcular(
            score = null,
            ratioEndeudamientoActual = null,
            capacidadPagoDisponible = null,
            ingresoMensual = null
        )

        assertEquals(NivelDiagnosticoFinanciero.INCOMPLETO, resultado.nivel)
    }
}