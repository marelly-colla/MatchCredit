package com.example.matchcredit.domain.model

import com.example.matchcredit.domain.enums.NivelRiesgo

data class ScoreResult(
    val score: Int,
    val nivelRiesgo: NivelRiesgo,
    val puntajeCapacidadPago: Int,
    val puntajeIngreso: Int,
    val puntajeTrabajo: Int,
    val puntajeAntiguedad: Int,
    val puntajeHistorial: Int,
    val puntajeAhorros: Int,
    val puntajeEdad: Int
)