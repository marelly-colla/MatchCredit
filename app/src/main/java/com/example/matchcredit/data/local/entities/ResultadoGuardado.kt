package com.example.matchcredit.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "resultados_guardados",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TipoPrestamo::class,
            parentColumns = ["tipoPrestamoId"],
            childColumns = ["tipoPrestamoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductoCrediticio::class,
            parentColumns = ["productoId"],
            childColumns = ["productoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["usuarioId"]),
        Index(value = ["tipoPrestamoId"]),
        Index(value = ["productoId"])
    ]
)
data class ResultadoGuardado(
    @PrimaryKey(autoGenerate = true)
    val resultadoId: Int = 0,

    // Datos para repetir la simulación
    val usuarioId: Int,
    val tipoPrestamoId: Int,
    val montoSolicitado: Double,
    val plazoMeses: Int,
    val fechaSimulacion: Long,
    val scoreUsado: Int,
    val nivelRiesgoUsado: String,

    // Producto guardado
    val productoId: Int,

    // Resultado obtenido
    val cumpleFiltros: Boolean,
    val cumpleCapacidadPago: Boolean,
    val motivosExclusion: String,
    val teaUsadaPct: Double,
    val temCalculada: Double,
    val cuotaBase: Double,
    val seguroMensual: Double,
    val cuotaEstimada: Double,
    val costoTotalEstimado: Double,
    val interesYSeguroTotal: Double,
    val ratioPostCredito: Double,
    val ranking: Int
)