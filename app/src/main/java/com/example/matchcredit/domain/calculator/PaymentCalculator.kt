package com.example.matchcredit.domain.calculator

import com.example.matchcredit.data.local.entities.ProductoCrediticio
import kotlin.math.pow
import kotlin.math.round

data class PaymentResult(
    val teaUsadaPct: Double,
    val temCalculada: Double,
    val cuotaBase: Double,
    val seguroMensual: Double,
    val cuotaEstimada: Double,
    val costoTotalEstimado: Double,
    val interesYSeguroTotal: Double
)

class PaymentCalculator {

    fun calcularDesdeProducto(
        montoSolicitado: Double,
        plazoMeses: Int,
        producto: ProductoCrediticio
    ): PaymentResult {
        return calcular(
            montoSolicitado = montoSolicitado,
            plazoMeses = plazoMeses,
            teaPct = producto.teaReferencialSbsPct,
            seguroDesgravamenMensualPct = producto.seguroDesgravamenMensualPct,
            comisionMensual = producto.comisionMensual,
            gastoAdministrativo = producto.gastoAdministrativo
        )
    }

    fun calcular(
        montoSolicitado: Double,
        plazoMeses: Int,
        teaPct: Double,
        seguroDesgravamenMensualPct: Double,
        comisionMensual: Double = 0.0,
        gastoAdministrativo: Double = 0.0
    ): PaymentResult {
        require(montoSolicitado > 0) {
            "El monto solicitado debe ser mayor a 0."
        }

        require(plazoMeses > 0) {
            "El plazo debe ser mayor a 0 meses."
        }

        require(teaPct >= 0) {
            "La TEA no puede ser negativa."
        }

        val tem = calcularTemDesdeTea(teaPct)

        val cuotaBase = if (tem == 0.0) {
            montoSolicitado / plazoMeses
        } else {
            val factor = (1 + tem).pow(plazoMeses)
            montoSolicitado * (tem * factor) / (factor - 1)
        }

        val seguroMensual = montoSolicitado * (seguroDesgravamenMensualPct / 100)

        val cuotaEstimada =
            cuotaBase + seguroMensual + comisionMensual + gastoAdministrativo

        val costoTotalEstimado = cuotaEstimada * plazoMeses

        val interesYSeguroTotal = costoTotalEstimado - montoSolicitado

        return PaymentResult(
            teaUsadaPct = redondear2(teaPct),
            temCalculada = redondear6(tem),
            cuotaBase = redondear2(cuotaBase),
            seguroMensual = redondear2(seguroMensual),
            cuotaEstimada = redondear2(cuotaEstimada),
            costoTotalEstimado = redondear2(costoTotalEstimado),
            interesYSeguroTotal = redondear2(interesYSeguroTotal)
        )
    }

    fun calcularTemDesdeTea(teaPct: Double): Double {
        return (1 + teaPct / 100).pow(1.0 / 12.0) - 1
    }

    fun calcularRatioPostCredito(
        ingresoMensual: Double,
        gastosMensuales: Double,
        cuotaMensualDeudas: Double,
        cuotaEstimada: Double
    ): Double {
        require(ingresoMensual > 0) {
            "El ingreso mensual debe ser mayor a 0."
        }

        val ratio = (gastosMensuales + cuotaMensualDeudas + cuotaEstimada) / ingresoMensual
        return redondear4(ratio)
    }

    fun cumpleCapacidadPago(
        ratioPostCredito: Double,
        limiteMaximo: Double = 0.60
    ): Boolean {
        return ratioPostCredito <= limiteMaximo
    }

    private fun redondear2(valor: Double): Double {
        return round(valor * 100) / 100
    }

    private fun redondear4(valor: Double): Double {
        return round(valor * 10000) / 10000
    }

    private fun redondear6(valor: Double): Double {
        return round(valor * 1000000) / 1000000
    }
}