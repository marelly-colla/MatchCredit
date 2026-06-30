package com.example.matchcredit.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "bancos")
data class Banco(
    @PrimaryKey
    val bancoId: Int,
    val nombre: String,
)