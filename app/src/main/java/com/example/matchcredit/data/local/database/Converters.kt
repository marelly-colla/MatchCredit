package com.example.matchcredit.data.local.database

import androidx.room.TypeConverter
import com.example.matchcredit.domain.enums.ClasificacionDeclarada
import com.example.matchcredit.domain.enums.NivelRiesgo
import com.example.matchcredit.domain.enums.TipoTrabajo


class Converters {

    // TipoTrabajo
    @TypeConverter
    fun fromTipoTrabajo(tipo: TipoTrabajo): String {
        return tipo.name
    }

    @TypeConverter
    fun toTipoTrabajo(valor: String): TipoTrabajo {
        return TipoTrabajo.valueOf(valor)
    }

    // ClasificacionDeclarada
    @TypeConverter
    fun fromClasificacion(clasificacion: ClasificacionDeclarada): String {
        return clasificacion.name
    }

    @TypeConverter
    fun toClasificacion(valor: String): ClasificacionDeclarada {
        return ClasificacionDeclarada.valueOf(valor)
    }

    // NivelRiesgo
    @TypeConverter
    fun fromNivelRiesgo(nivel: NivelRiesgo): String {
        return nivel.name
    }

    @TypeConverter
    fun toNivelRiesgo(valor: String): NivelRiesgo {
        return NivelRiesgo.valueOf(valor)
    }
}