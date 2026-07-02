package com.example.matchcredit.domain.calculator

enum class NivelDiagnosticoFinanciero {
    SALUDABLE,
    MODERADO,
    RIESGOSO,
    INCOMPLETO
}

data class DiagnosticoFinanciero(
    val titulo: String,
    val mensaje: String,
    val nivel: NivelDiagnosticoFinanciero,
    val ratioEndeudamientoPct: Double?,
    val capacidadPagoDisponible: Double?,
    val recomendacionPrincipal: String
)

object DiagnosticoFinancieroCalculator {

    fun calcular(
        score: Int?,
        ratioEndeudamientoActual: Double?,
        capacidadPagoDisponible: Double?,
        ingresoMensual: Double?
    ): DiagnosticoFinanciero {
        if (
            score == null ||
            ratioEndeudamientoActual == null ||
            capacidadPagoDisponible == null ||
            ingresoMensual == null ||
            ingresoMensual <= 0.0
        ) {
            return DiagnosticoFinanciero(
                titulo = "Diagnóstico pendiente",
                mensaje = "Completa tu perfil financiero para recibir una evaluación más precisa.",
                nivel = NivelDiagnosticoFinanciero.INCOMPLETO,
                ratioEndeudamientoPct = null,
                capacidadPagoDisponible = null,
                recomendacionPrincipal = "Registra tus ingresos, gastos, deudas y datos laborales."
            )
        }

        val ratioPct = ratioEndeudamientoActual * 100

        return when {
            ratioEndeudamientoActual <= 0.35 &&
                    capacidadPagoDisponible > 0.0 &&
                    score >= 80 -> {
                DiagnosticoFinanciero(
                    titulo = "Perfil financiero saludable",
                    mensaje = "Tu nivel de deuda actual es manejable y tu capacidad de pago es positiva.",
                    nivel = NivelDiagnosticoFinanciero.SALUDABLE,
                    ratioEndeudamientoPct = ratioPct,
                    capacidadPagoDisponible = capacidadPagoDisponible,
                    recomendacionPrincipal = "Puedes evaluar préstamos de bajo o mediano monto sin descuidar tu presupuesto."
                )
            }

            ratioEndeudamientoActual <= 0.60 &&
                    capacidadPagoDisponible > 0.0 &&
                    score >= 60 -> {
                DiagnosticoFinanciero(
                    titulo = "Perfil financiero moderado",
                    mensaje = "Puedes evaluar opciones de préstamo, pero debes cuidar que la nueva cuota no eleve demasiado tu deuda mensual.",
                    nivel = NivelDiagnosticoFinanciero.MODERADO,
                    ratioEndeudamientoPct = ratioPct,
                    capacidadPagoDisponible = capacidadPagoDisponible,
                    recomendacionPrincipal = "Compara montos y plazos antes de elegir una opción."
                )
            }

            else -> {
                DiagnosticoFinanciero(
                    titulo = "Perfil financiero en riesgo",
                    mensaje = "Tu nivel de deuda o capacidad de pago puede verse comprometido si asumes una nueva cuota alta.",
                    nivel = NivelDiagnosticoFinanciero.RIESGOSO,
                    ratioEndeudamientoPct = ratioPct,
                    capacidadPagoDisponible = capacidadPagoDisponible,
                    recomendacionPrincipal = "Reduce deudas, ajusta el monto solicitado o amplía el plazo antes de solicitar un préstamo."
                )
            }
        }
    }
}