package com.example.matchcredit.domain.calculator

import kotlin.math.pow

enum class NivelMontoRecomendado {
    DISPONIBLE,
    LIMITADO,
    NO_RECOMENDADO,
    INCOMPLETO
}

data class MontoRecomendado(
    val nivel: NivelMontoRecomendado,
    val titulo: String,
    val mensaje: String,
    val cuotaMaximaSaludable: Double,
    val montoMaximoEstimado: Double,
    val plazoUsadoMeses: Int,
    val teaReferencialPct: Double,
    val recomendacion: String
)

object MontoRecomendadoCalculator {

    private const val RATIO_SALUDABLE_MAXIMO = 0.35
    private const val SEGURO_MENSUAL_REFERENCIAL = 0.00069

    fun calcular(
        ingresoMensual: Double?,
        gastosMensuales: Double?,
        cuotaMensualDeudas: Double?,
        capacidadPagoDisponible: Double?,
        tipoPrestamo: String,
        plazoMeses: Int?
    ): MontoRecomendado {
        val ingreso = ingresoMensual ?: 0.0
        val gastos = gastosMensuales ?: 0.0
        val cuotaDeudas = cuotaMensualDeudas ?: 0.0

        if (ingreso <= 0.0) {
            return MontoRecomendado(
                nivel = NivelMontoRecomendado.INCOMPLETO,
                titulo = "Completa tu perfil financiero",
                mensaje = "Necesitamos tus ingresos, gastos y deudas para calcular una orientación de monto.",
                cuotaMaximaSaludable = 0.0,
                montoMaximoEstimado = 0.0,
                plazoUsadoMeses = plazoMeses ?: 24,
                teaReferencialPct = obtenerTeaReferencial(tipoPrestamo),
                recomendacion = "Registra tu perfil financiero antes de comparar préstamos."
            )
        }

        val plazoSeguro = plazoMeses
            ?.coerceIn(1, 360)
            ?: plazoSugeridoPorTipo(tipoPrestamo)

        val teaReferencial = obtenerTeaReferencial(tipoPrestamo)
        val compromisoActual = gastos + cuotaDeudas

        val cuotaMaximaPorRatio = (ingreso * RATIO_SALUDABLE_MAXIMO) - compromisoActual
        val capacidadDisponible = capacidadPagoDisponible ?: (ingreso - compromisoActual)

        val cuotaMaximaSaludable = minOf(
            cuotaMaximaPorRatio,
            capacidadDisponible
        ).coerceAtLeast(0.0)

        if (cuotaMaximaSaludable <= 0.0) {
            return MontoRecomendado(
                nivel = NivelMontoRecomendado.NO_RECOMENDADO,
                titulo = "No se recomienda asumir una nueva cuota",
                mensaje = "Con tus datos actuales, una cuota adicional podría elevar demasiado tu nivel de endeudamiento.",
                cuotaMaximaSaludable = 0.0,
                montoMaximoEstimado = 0.0,
                plazoUsadoMeses = plazoSeguro,
                teaReferencialPct = teaReferencial,
                recomendacion = "Reduce gastos o deudas antes de solicitar un préstamo."
            )
        }

        val montoEstimado = estimarMontoDesdeCuota(
            cuotaMensual = cuotaMaximaSaludable,
            plazoMeses = plazoSeguro,
            teaPct = teaReferencial
        )

        val nivel = when {
            montoEstimado >= 10000.0 -> NivelMontoRecomendado.DISPONIBLE
            montoEstimado >= 2000.0 -> NivelMontoRecomendado.LIMITADO
            else -> NivelMontoRecomendado.NO_RECOMENDADO
        }

        return when (nivel) {
            NivelMontoRecomendado.DISPONIBLE -> {
                MontoRecomendado(
                    nivel = nivel,
                    titulo = "Tienes margen para simular un préstamo",
                    mensaje = "Según tu perfil, existe una cuota saludable aproximada que podrías usar como referencia.",
                    cuotaMaximaSaludable = cuotaMaximaSaludable,
                    montoMaximoEstimado = montoEstimado,
                    plazoUsadoMeses = plazoSeguro,
                    teaReferencialPct = teaReferencial,
                    recomendacion = "Puedes usar este monto como punto de partida y luego comparar opciones."
                )
            }

            NivelMontoRecomendado.LIMITADO -> {
                MontoRecomendado(
                    nivel = nivel,
                    titulo = "Tu margen de préstamo es limitado",
                    mensaje = "Puedes simular un préstamo, pero conviene empezar con montos bajos o ampliar el plazo.",
                    cuotaMaximaSaludable = cuotaMaximaSaludable,
                    montoMaximoEstimado = montoEstimado,
                    plazoUsadoMeses = plazoSeguro,
                    teaReferencialPct = teaReferencial,
                    recomendacion = "Evita solicitar montos altos para no comprometer tu presupuesto."
                )
            }

            NivelMontoRecomendado.NO_RECOMENDADO -> {
                MontoRecomendado(
                    nivel = nivel,
                    titulo = "Monto recomendado muy bajo",
                    mensaje = "El monto estimado es bajo porque tu capacidad saludable disponible es reducida.",
                    cuotaMaximaSaludable = cuotaMaximaSaludable,
                    montoMaximoEstimado = montoEstimado,
                    plazoUsadoMeses = plazoSeguro,
                    teaReferencialPct = teaReferencial,
                    recomendacion = "Revisa tus gastos o deudas antes de solicitar un crédito."
                )
            }

            NivelMontoRecomendado.INCOMPLETO -> {
                MontoRecomendado(
                    nivel = nivel,
                    titulo = "Completa tu perfil financiero",
                    mensaje = "Necesitamos más información para calcular una orientación.",
                    cuotaMaximaSaludable = 0.0,
                    montoMaximoEstimado = 0.0,
                    plazoUsadoMeses = plazoSeguro,
                    teaReferencialPct = teaReferencial,
                    recomendacion = "Completa tu perfil financiero."
                )
            }
        }
    }

    private fun estimarMontoDesdeCuota(
        cuotaMensual: Double,
        plazoMeses: Int,
        teaPct: Double
    ): Double {
        val tem = (1 + teaPct / 100.0).pow(1.0 / 12.0) - 1.0

        val factorCuota = if (tem == 0.0) {
            1.0 / plazoMeses
        } else {
            val potencia = (1 + tem).pow(plazoMeses)
            tem * potencia / (potencia - 1)
        }

        val factorTotal = factorCuota + SEGURO_MENSUAL_REFERENCIAL

        if (factorTotal <= 0.0) {
            return 0.0
        }

        return cuotaMensual / factorTotal
    }

    private fun obtenerTeaReferencial(
        tipoPrestamo: String
    ): Double {
        return when (tipoPrestamo.lowercase()) {
            "hipotecario" -> 11.5
            "vehicular" -> 18.0
            else -> 28.0
        }
    }

    private fun plazoSugeridoPorTipo(
        tipoPrestamo: String
    ): Int {
        return when (tipoPrestamo.lowercase()) {
            "hipotecario" -> 120
            "vehicular" -> 48
            else -> 24
        }
    }
}