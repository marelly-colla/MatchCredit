package com.example.matchcredit.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.matchcredit.domain.enums.ClasificacionDeclarada
import com.example.matchcredit.domain.enums.NivelRiesgo
import com.example.matchcredit.domain.enums.TipoTrabajo

@Entity(
    tableName = "perfiles_financieros",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["usuarioId"])]
)
data class PerfilFinanciero(
    @PrimaryKey(autoGenerate = true)
    val perfilId: Int = 0,

    val usuarioId: Int,

    val tipoTrabajo: TipoTrabajo,
    val ingresoMensual: Double,
    val gastosMensuales: Double,
    val deudaTotalActual: Double,
    val cuotaMensualDeudas: Double,
    val antiguedadTrabajandoMeses: Int,
    val clasificacionSbsDeclarada: ClasificacionDeclarada,
    val tieneAhorros: Boolean,
    val montoAhorros: Double,
    val scoreMatchcredit: Int,
    val nivelRiesgo: NivelRiesgo,
    val ratioEndeudamientoActual: Double,
    val capacidadPagoDisponible: Double
)