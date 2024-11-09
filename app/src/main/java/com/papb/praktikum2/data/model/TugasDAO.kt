package com.papb.praktikum2.data.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TugasDAO {
    @Query("SELECT * FROM tugas ORDER BY id DESC")
    fun getAllTugas(): LiveData<List<Tugas>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTugas(tugas: Tugas)

    @Update
    suspend fun updateTugas(tugas: Tugas)
}