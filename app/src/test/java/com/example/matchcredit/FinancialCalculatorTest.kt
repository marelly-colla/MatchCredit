package com.example.matchcredit

import com.example.matchcredit.domain.calculator.FinancialCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class FinancialCalculatorTest {

    @Test
    fun calcularRatioEndeudamiento_conDatosValidos_retornaRatioCorrecto() {
        val ratio = FinancialCalculator.calcularRatioEndeudamiento(
            ingreso = 5500.0,
            gastos = 1200.0,
            cuotaDeudas = 25.0
        )

        assertEquals(0.2227, ratio, 0.0001)
    }

    @Test
    fun calcularCapacidadPagoDisponible_conDatosValidos_retornaMontoCorrecto() {
        val capacidad = FinancialCalculator.calcularCapacidadPagoDisponible(
            ingreso = 5500.0,
            gastos = 1200.0,
            cuotaDeudas = 25.0
        )

        assertEquals(4275.0, capacidad, 0.01)
    }
}