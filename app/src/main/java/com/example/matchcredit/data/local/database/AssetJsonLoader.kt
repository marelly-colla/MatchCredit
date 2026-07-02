package com.example.matchcredit.data.local.database

import android.content.Context
import com.example.matchcredit.data.local.entities.Banco
import com.example.matchcredit.data.local.entities.ProductoCrediticio
import com.example.matchcredit.data.local.entities.TipoPrestamo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object AssetJsonLoader {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private fun leerAsset(
        context: Context,
        fileName: String
    ): String {
        return context.assets
            .open(fileName)
            .bufferedReader()
            .use { reader ->
                reader.readText()
            }
    }

    fun cargarBancos(
        context: Context,
        fileName: String = "bancos.json"
    ): List<Banco> {
        val texto = leerAsset(
            context = context,
            fileName = fileName
        )

        return json.decodeFromString<List<Banco>>(texto)
    }

    fun cargarTiposPrestamo(
        context: Context,
        fileName: String = "tipos_prestamo.json"
    ): List<TipoPrestamo> {
        val texto = leerAsset(
            context = context,
            fileName = fileName
        )

        return json.decodeFromString<List<TipoPrestamo>>(texto)
    }

    fun cargarProductosCrediticios(
        context: Context,
        fileName: String = "productos_crediticios.json"
    ): List<ProductoCrediticio> {
        val texto = leerAsset(
            context = context,
            fileName = fileName
        )

        return json.decodeFromString<List<ProductoCrediticio>>(texto)
    }
}