package com.example.matchcredit.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.TypeConverters
import com.example.matchcredit.data.local.dao.*
import com.example.matchcredit.data.local.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Usuario::class,
        PerfilFinanciero::class,
        Banco::class,
        TipoPrestamo::class,
        ProductoCrediticio::class,
        ResultadoGuardado::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MatchCreditDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun perfilFinancieroDao(): PerfilFinancieroDao
    abstract fun bancoDao(): BancoDao
    abstract fun tipoPrestamoDao(): TipoPrestamoDao
    abstract fun productoCrediticioDao(): ProductoCrediticioDao
    abstract fun resultadoGuardadoDao(): ResultadoGuardadoDao

    companion object {
        @Volatile
        private var INSTANCE: MatchCreditDatabase? = null

        fun getDatabase(context: Context): MatchCreditDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MatchCreditDatabase::class.java,
                    "matchcredit_database"
                )
                    .addCallback(SeedDatabaseCallback(context.applicationContext))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

private class SeedDatabaseCallback(
    private val context: Context
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Se ejecuta solo la primera vez que se crea la base de datos
        CoroutineScope(Dispatchers.IO).launch {
            val database = MatchCreditDatabase.getDatabase(context)
            seedData(database, context)
        }
    }
    private suspend fun seedData(database: MatchCreditDatabase, context: Context) {
        val bancos = AssetJsonLoader.cargarBancos(context)
        val tiposPrestamo = AssetJsonLoader.cargarTiposPrestamo(context)
        val productos = AssetJsonLoader.cargarProductosCrediticios(context)

        // Orden importa por las foreign keys: primero bancos y tipos, luego productos
        database.bancoDao().insertarTodos(bancos)
        database.tipoPrestamoDao().insertarTodos(tiposPrestamo)
        database.productoCrediticioDao().insertarTodos(productos)
    }
}

