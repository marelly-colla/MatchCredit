package com.example.matchcredit

import com.example.matchcredit.data.local.entities.PerfilFinanciero
import com.example.matchcredit.data.local.entities.ProductoCrediticio
import com.example.matchcredit.domain.calculator.PaymentCalculator
import com.example.matchcredit.domain.calculator.RankingCalculator
import com.example.matchcredit.domain.enums.ClasificacionDeclarada
import com.example.matchcredit.domain.enums.NivelRiesgo
import com.example.matchcredit.domain.enums.TipoTrabajo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RankingCalculatorTest {

    private val rankingCalculator = RankingCalculator()
    private val paymentCalculator = PaymentCalculator()

    @Test
    fun obtenerTipoPrestamoId_personal_retornaUno() {
        val tipoId = rankingCalculator.obtenerTipoPrestamoId("Personal")

        assertEquals(1, tipoId)
    }

    @Test
    fun evaluarProducto_usuarioCumple_retornaRecomendado() {
        val perfil = crearPerfilBueno()
        val producto = crearProductoPersonal(
            productoId = 1,
            tea = 16.49,
            ingresoMin = 1500.0,
            minTrabajo = 6,
            montoMin = 1000.0,
            montoMax = 150000.0,
            plazoMin = 6,
            plazoMax = 60
        )

        val resultado = rankingCalculator.evaluarProducto(
            producto = producto,
            bancoNombre = "Interbank",
            perfil = perfil,
            montoSolicitado = 5000.0,
            plazoMeses = 24,
            paymentCalculator = paymentCalculator
        )

        assertTrue(resultado.cumpleFiltros)
        assertTrue(resultado.cumpleCapacidadPago)
        assertTrue(resultado.esRecomendado)
        assertEquals(0, resultado.motivosExclusion.size)
    }

    @Test
    fun evaluarProducto_montoMenorAlMinimo_agregaMotivoExclusion() {
        val perfil = crearPerfilBueno()
        val producto = crearProductoPersonal(
            productoId = 2,
            tea = 12.15,
            ingresoMin = 1500.0,
            minTrabajo = 3,
            montoMin = 15000.0,
            montoMax = 120000.0,
            plazoMin = 6,
            plazoMax = 72
        )

        val resultado = rankingCalculator.evaluarProducto(
            producto = producto,
            bancoNombre = "BCP",
            perfil = perfil,
            montoSolicitado = 5000.0,
            plazoMeses = 24,
            paymentCalculator = paymentCalculator
        )

        assertEquals(false, resultado.cumpleFiltros)
        assertTrue(
            resultado.motivosExclusion.any {
                it.contains("monto", ignoreCase = true)
            }
        )
    }

    @Test
    fun ordenarResultados_colocaRecomendadosPrimero() {
        val perfil = crearPerfilBueno()

        val recomendado = rankingCalculator.evaluarProducto(
            producto = crearProductoPersonal(
                productoId = 1,
                tea = 16.49,
                ingresoMin = 1500.0,
                minTrabajo = 6,
                montoMin = 1000.0,
                montoMax = 150000.0,
                plazoMin = 6,
                plazoMax = 60
            ),
            bancoNombre = "Interbank",
            perfil = perfil,
            montoSolicitado = 5000.0,
            plazoMeses = 24,
            paymentCalculator = paymentCalculator
        )

        val noCumple = rankingCalculator.evaluarProducto(
            producto = crearProductoPersonal(
                productoId = 2,
                tea = 12.15,
                ingresoMin = 1500.0,
                minTrabajo = 3,
                montoMin = 15000.0,
                montoMax = 120000.0,
                plazoMin = 6,
                plazoMax = 72
            ),
            bancoNombre = "BCP",
            perfil = perfil,
            montoSolicitado = 5000.0,
            plazoMeses = 24,
            paymentCalculator = paymentCalculator
        )

        val ordenados = rankingCalculator.ordenarResultados(
            listOf(noCumple, recomendado)
        )

        assertEquals("Interbank", ordenados.first().bancoNombre)
        assertEquals(1, ordenados.first().ranking)
    }

    private fun crearPerfilBueno(): PerfilFinanciero {
        return PerfilFinanciero(
            usuarioId = 1,
            tipoTrabajo = TipoTrabajo.PLANILLA,
            ingresoMensual = 5500.0,
            gastosMensuales = 1200.0,
            deudaTotalActual = 3000.0,
            cuotaMensualDeudas = 25.0,
            antiguedadTrabajandoMeses = 36,
            clasificacionSbsDeclarada = ClasificacionDeclarada.NORMAL,
            tieneAhorros = true,
            montoAhorros = 2500.0,
            scoreMatchcredit = 90,
            nivelRiesgo = NivelRiesgo.ALTA_COMPATIBILIDAD,
            ratioEndeudamientoActual = 0.223,
            capacidadPagoDisponible = 4275.0
        )
    }

    private fun crearProductoPersonal(
        productoId: Int,
        tea: Double,
        ingresoMin: Double,
        minTrabajo: Int,
        montoMin: Double,
        montoMax: Double,
        plazoMin: Int,
        plazoMax: Int
    ): ProductoCrediticio {
        return ProductoCrediticio(
            productoId = productoId,
            bancoId = productoId,
            tipoPrestamoId = 1,
            nombreProducto = "Préstamo Personal",
            teaReferencialSbsPct = tea,
            minTrabajoMeses = minTrabajo,
            montoMin = montoMin,
            montoMax = montoMax,
            plazoMinMeses = plazoMin,
            plazoMaxMeses = plazoMax,
            ingresoMin = ingresoMin,
            seguroDesgravamenMensualPct = 0.069,
            comisionMensual = 0.0,
            gastoAdministrativo = 0.0,
            urlBanco = "",
            requisitosTexto = "DNI, sustento de ingresos y evaluación crediticia."
        )
    }
}