package com.example.matchcredit.domain.calculator

import com.example.matchcredit.data.local.entities.PerfilFinanciero
import com.example.matchcredit.data.local.entities.ProductoCrediticio

data class ResultadoPrestamoCalculado(
    val producto: ProductoCrediticio,
    val bancoNombre: String,
    val paymentResult: PaymentResult,
    val cumpleFiltros: Boolean,
    val cumpleCapacidadPago: Boolean,
    val motivosExclusion: List<String>,
    val ratioPostCredito: Double,
    val ranking: Int? = null
) {
    val esRecomendado: Boolean
        get() = cumpleFiltros && cumpleCapacidadPago
}

class RankingCalculator {

    fun obtenerTipoPrestamoId(tipoPrestamo: String): Int {
        return when (tipoPrestamo.lowercase()) {
            "personal" -> 1
            "hipotecario" -> 2
            "vehicular" -> 3
            else -> 1
        }
    }

    fun evaluarProducto(
        producto: ProductoCrediticio,
        bancoNombre: String,
        perfil: PerfilFinanciero,
        montoSolicitado: Double,
        plazoMeses: Int,
        paymentCalculator: PaymentCalculator
    ): ResultadoPrestamoCalculado {
        val motivos = mutableListOf<String>()

        if (montoSolicitado < producto.montoMin) {
            motivos.add("El monto solicitado es menor al mínimo permitido.")
        }

        if (montoSolicitado > producto.montoMax) {
            motivos.add("El monto solicitado supera el máximo permitido.")
        }

        if (plazoMeses < producto.plazoMinMeses) {
            motivos.add("El plazo solicitado es menor al plazo mínimo.")
        }

        if (plazoMeses > producto.plazoMaxMeses) {
            motivos.add("El plazo solicitado supera el plazo máximo.")
        }

        if (perfil.ingresoMensual < producto.ingresoMin) {
            motivos.add("No cumple con el ingreso mínimo requerido.")
        }

        if (perfil.antiguedadTrabajandoMeses < producto.minTrabajoMeses) {
            motivos.add("No cumple con el tiempo mínimo de trabajo.")
        }

        val cumpleFiltrosBasicos = motivos.isEmpty()

        val pago = paymentCalculator.calcularDesdeProducto(
            montoSolicitado = montoSolicitado,
            plazoMeses = plazoMeses,
            producto = producto
        )

        val ratioPostCredito = paymentCalculator.calcularRatioPostCredito(
            ingresoMensual = perfil.ingresoMensual,
            gastosMensuales = perfil.gastosMensuales,
            cuotaMensualDeudas = perfil.cuotaMensualDeudas,
            cuotaEstimada = pago.cuotaEstimada
        )

        val cumpleCapacidadPago = paymentCalculator.cumpleCapacidadPago(
            ratioPostCredito = ratioPostCredito
        )

        if (!cumpleCapacidadPago) {
            motivos.add("No recomendado por capacidad de pago.")
        }

        return ResultadoPrestamoCalculado(
            producto = producto,
            bancoNombre = bancoNombre,
            paymentResult = pago,
            cumpleFiltros = cumpleFiltrosBasicos,
            cumpleCapacidadPago = cumpleCapacidadPago,
            motivosExclusion = motivos,
            ratioPostCredito = ratioPostCredito
        )
    }

    fun ordenarResultados(
        resultados: List<ResultadoPrestamoCalculado>
    ): List<ResultadoPrestamoCalculado> {
        return resultados
            .sortedWith(
                compareByDescending<ResultadoPrestamoCalculado> { it.esRecomendado }
                    .thenByDescending { it.cumpleFiltros }
                    .thenBy { it.paymentResult.cuotaEstimada }
            )
            .mapIndexed { index, resultado ->
                resultado.copy(ranking = index + 1)
            }
    }
}