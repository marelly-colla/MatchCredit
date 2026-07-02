package com.example.matchcredit

import com.example.matchcredit.domain.calculator.PaymentCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PaymentCalculatorTest {

    private val calculator = PaymentCalculator()

    @Test
    fun calcularTemDesdeTea_conTeaValida_retornaTemAproximada() {
        val tem = calculator.calcularTemDesdeTea(19.54)

        assertEquals(0.01495, tem, 0.0001)
    }

    @Test
    fun calcular_conDatosValidos_retornaCuotaCostoEInteres() {
        val resultado = calculator.calcular(
            montoSolicitado = 5000.0,
            plazoMeses = 24,
            teaPct = 19.54,
            seguroDesgravamenMensualPct = 0.069,
            comisionMensual = 0.0,
            gastoAdministrativo = 0.0
        )

        assertEquals(19.54, resultado.teaUsadaPct, 0.01)
        assertEquals(253.03, resultado.cuotaEstimada, 0.1)
        assertEquals(6072.62, resultado.costoTotalEstimado, 0.1)
        assertEquals(1072.62, resultado.interesYSeguroTotal, 0.1)
    }

    @Test
    fun calcularRatioPostCredito_conDatosValidos_retornaRatioCorrecto() {
        val ratio = calculator.calcularRatioPostCredito(
            ingresoMensual = 5500.0,
            gastosMensuales = 1200.0,
            cuotaMensualDeudas = 25.0,
            cuotaEstimada = 253.03
        )

        assertEquals(0.2687, ratio, 0.0001)
    }

    @Test
    fun cumpleCapacidadPago_ratioMenorA60_retornaTrue() {
        val cumple = calculator.cumpleCapacidadPago(
            ratioPostCredito = 0.45
        )

        assertEquals(true, cumple)
    }

    @Test
    fun calcular_montoCero_lanzaExcepcion() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.calcular(
                montoSolicitado = 0.0,
                plazoMeses = 24,
                teaPct = 19.54,
                seguroDesgravamenMensualPct = 0.069
            )
        }
    }
}