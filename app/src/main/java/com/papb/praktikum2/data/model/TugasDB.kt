package com.papb.praktikum2.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Tugas::class], version = 2)
abstract class TugasDB : RoomDatabase() {
    abstract fun tugasDAO(): TugasDAO

    companion object {
        @Volatile
        private var INSTANCE: TugasDB? = null

        // Migration from version 1 to 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE tugas ADD COLUMN image_uri TEXT"
                )
            }
        }

        @JvmStatic
        fun getDatabase(context: Context): TugasDB {
            if (INSTANCE == null) {
                synchronized(TugasDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TugasDB::class.java,
                        "tugas_database"
                    )
                        .addMigrations(MIGRATION_1_2) // Add migration
                        .build()
                }
            }
            return INSTANCE as TugasDB
        }
    }
}