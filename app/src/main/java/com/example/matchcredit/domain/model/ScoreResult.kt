package com.example.matchcredit.domain.model

import com.example.matchcredit.domain.enums.NivelRiesgo

data class ScoreResult(
    val score: Int,
    val nivelRiesgo: NivelRiesgo,
    val capacidadPago: Int,
    val ingreso: Int,
    val trabajo: Int,
    val antiguedad: Int,
    val historial: Int,
    val ahorros: Int,
    val edad: Int
)