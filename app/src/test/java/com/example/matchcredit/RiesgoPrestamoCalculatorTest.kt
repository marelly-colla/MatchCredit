package com.example.matchcredit

import com.example.matchcredit.domain.calculator.NivelRiesgoPrestamo
import com.example.matchcredit.domain.calculator.RiesgoPrestamoCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class RiesgoPrestamoCalculatorTest {

    @Test
    fun calcular_ratioMenorA35_retornaRiesgoBajo() {
        val resultado = RiesgoPrestamoCalculator.calcular(
            ratioPostCredito = 0.30
        )

        assertEquals(NivelRiesgoPrestamo.BAJO, resultado.nivel)
        assertEquals("Riesgo bajo", resultado.titulo)
        assertEquals(30.0, resultado.ratioPostCreditoPct, 0.01)
    }

    @Test
    fun calcular_ratioEntre35Y60_retornaRiesgoModerado() {
        val resultado = RiesgoPrestamoCalculator.calcular(
            ratioPostCredito = 0.45
        )

        assertEquals(NivelRiesgoPrestamo.MODERADO, resultado.nivel)
        assertEquals("Riesgo moderado", resultado.titulo)
        assertEquals(45.0, resultado.ratioPostCreditoPct, 0.01)
    }

    @Test
    fun calcular_ratioMayorA60_retornaRiesgoAlto() {
        val resultado = RiesgoPrestamoCalculator.calcular(
            ratioPostCredito = 0.72
        )

        assertEquals(NivelRiesgoPrestamo.ALTO, resultado.nivel)
        assertEquals("Riesgo alto", resultado.titulo)
        assertEquals(72.0, resultado.ratioPostCreditoPct, 0.01)
    }
}