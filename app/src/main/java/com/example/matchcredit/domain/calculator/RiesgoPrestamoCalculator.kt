package com.example.matchcredit.domain.calculator

enum class NivelRiesgoPrestamo {
    BAJO,
    MODERADO,
    ALTO
}

data class RiesgoPrestamo(
    val nivel: NivelRiesgoPrestamo,
    val titulo: String,
    val mensaje: String,
    val ratioPostCreditoPct: Double
)

object RiesgoPrestamoCalculator {

    fun calcular(
        ratioPostCredito: Double
    ): RiesgoPrestamo {
        val ratioPct = ratioPostCredito * 100

        return when {
            ratioPostCredito <= 0.35 -> {
                RiesgoPrestamo(
                    nivel = NivelRiesgoPrestamo.BAJO,
                    titulo = "Riesgo bajo",
                    mensaje = "La cuota estimada se mantiene dentro de un rango saludable para tu perfil financiero.",
                    ratioPostCreditoPct = ratioPct
                )
            }

            ratioPostCredito <= 0.60 -> {
                RiesgoPrestamo(
                    nivel = NivelRiesgoPrestamo.MODERADO,
                    titulo = "Riesgo moderado",
                    mensaje = "La cuota puede ser manejable, pero conviene revisar tus gastos y evitar asumir más deudas.",
                    ratioPostCreditoPct = ratioPct
                )
            }

            else -> {
                RiesgoPrestamo(
                    nivel = NivelRiesgoPrestamo.ALTO,
                    titulo = "Riesgo alto",
                    mensaje = "Esta opción puede comprometer tu capacidad de pago mensual. Evalúa reducir el monto o ampliar el plazo.",
                    ratioPostCreditoPct = ratioPct
                )
            }
        }
    }
}