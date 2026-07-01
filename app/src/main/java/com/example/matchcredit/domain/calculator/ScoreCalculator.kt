package com.example.matchcredit.domain.calculator

import com.example.matchcredit.domain.enums.ClasificacionDeclarada
import com.example.matchcredit.domain.enums.NivelRiesgo
import com.example.matchcredit.domain.enums.TipoTrabajo
import com.example.matchcredit.domain.model.ScoreResult

class ScoreCalculator {
    fun calcularScore(
        ingresoMensual: Double,
        gastosMensuales: Double,
        cuotaMensualDeudas: Double,
        tipoTrabajo: TipoTrabajo,
        antiguedadMeses: Int,
        clasificacionCrediticia: ClasificacionDeclarada,
        montoAhorros: Double,
        edad: Int
    ): ScoreResult {
        val ratio = FinancialCalculator.calcularRatioEndeudamiento(
            ingresoMensual,
            gastosMensuales,
            cuotaMensualDeudas
        )
        val puntajeCapacidadPago = calcularPuntajeCapacidadPago(ratio)

        val puntajeIngreso = calcularPuntajeIngreso(ingresoMensual)

        val puntajeTrabajo = calcularPuntajeTrabajo(tipoTrabajo)

        val puntajeAntiguedad = calcularPuntajeAntiguedad(antiguedadMeses)

        val puntajeHistorial = calcularPuntajeHistorial(clasificacionCrediticia)

        val puntajeAhorros = calcularPuntajeAhorros(
            montoAhorros,
            ingresoMensual
        )

        val puntajeEdad = calcularPuntajeEdad(edad)

        val scoreTotal =
            puntajeCapacidadPago +
                    puntajeIngreso +
                    puntajeTrabajo +
                    puntajeAntiguedad +
                    puntajeHistorial +
                    puntajeAhorros +
                    puntajeEdad
        val nivelRiesgo = obtenerNivelRiesgo(scoreTotal)

        return ScoreResult(
            score = scoreTotal,
            nivelRiesgo = nivelRiesgo,
            puntajeCapacidadPago = puntajeCapacidadPago,
            puntajeIngreso = puntajeIngreso,
            puntajeTrabajo = puntajeTrabajo,
            puntajeAntiguedad = puntajeAntiguedad,
            puntajeHistorial = puntajeHistorial,
            puntajeAhorros = puntajeAhorros,
            puntajeEdad = puntajeEdad
        )
    }

    private fun calcularPuntajeCapacidadPago(
        ratio: Double,
    ): Int {
        return when {
            ratio <= 0.30 -> 30
            ratio <= 0.45 -> 20
            ratio <= 0.60 -> 10
            ratio <= 0.75 -> 5
            else -> 0
        }
    }

    private fun calcularPuntajeIngreso(
        ingreso: Double
    ): Int {
        return when {
            ingreso >= 5000 -> 20
            ingreso >= 3000 -> 15
            ingreso >= 1500 -> 10
            else -> 5
        }
    }

    private fun calcularPuntajeTrabajo(
        tipoTrabajo: TipoTrabajo
    ): Int {
        return when (tipoTrabajo) {
            TipoTrabajo.PLANILLA,
            TipoTrabajo.CONTRATO -> 10
            TipoTrabajo.INDEPENDIENTE_ESTABLE -> 8
            TipoTrabajo.TEMPORAL -> 5
            TipoTrabajo.INFORMAL -> 0
        }
    }

    private fun calcularPuntajeAntiguedad(
        meses: Int
    ): Int {
        return when {
            meses >= 24 -> 10
            meses >= 12 -> 7
            meses >= 6 -> 4
            else -> 0
        }
    }

    private fun calcularPuntajeHistorial(
        clasificacion: ClasificacionDeclarada
    ): Int {
        return when (clasificacion) {
            ClasificacionDeclarada.NORMAL -> 15
            ClasificacionDeclarada.CPP -> 8
            ClasificacionDeclarada.DESCONOCIDO -> 5
            ClasificacionDeclarada.DEFICIENTE -> 0
            ClasificacionDeclarada.DUDOSO -> 0
            ClasificacionDeclarada.PERDIDA -> 0
        }
    }

    private fun calcularPuntajeAhorros(
        ahorros: Double,
        ingreso: Double
    ): Int {
        return when {
            ahorros >= ingreso -> 10
            ahorros > 0 -> 5
            else -> 0
        }
    }

    private fun calcularPuntajeEdad(
        edad: Int
    ): Int {
        return if (edad in 18..65) {
            5
        } else {
            0
        }
    }
    private fun obtenerNivelRiesgo(score: Int): NivelRiesgo {
        return when (score) {
            in 80..100 -> NivelRiesgo.ALTA_COMPATIBILIDAD
            in 60..79 -> NivelRiesgo.COMPATIBILIDAD_MEDIA
            in 40..59 -> NivelRiesgo.COMPATIBILIDAD_BAJA
            else -> NivelRiesgo.RIESGO_ALTO
        }
    }
}