package com.example.matchcredit.domain.calculator

class FinancialCalculator {
    fun calcularRatioEndeudamiento(
        ingreso: Double,
        gastos: Double,
        cuotaDeudas: Double
    ): Double {
        return (gastos + cuotaDeudas) / ingreso
    }

    fun calcularCapacidadPagoDisponible(
        ingreso: Double,
        gastos: Double,
        cuotaDeudas: Double
    ): Double {
        return ingreso - gastos - cuotaDeudas
    }
}