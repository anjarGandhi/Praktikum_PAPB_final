package com.papb.praktikum2.data.model

import android.app.Application
import androidx.lifecycle.LiveData

class TugasRepository(application: Application) {
    private val mTugasDAO: TugasDAO

    init {
        val db = TugasDB.getDatabase(application)
        mTugasDAO = db.tugasDAO()
    }

    fun getAllTugas(): LiveData<List<Tugas>> = mTugasDAO.getAllTugas()

    suspend fun insert(tugas: Tugas) {
        mTugasDAO.insertTugas(tugas)
    }

    suspend fun update(tugas: Tugas) {
        mTugasDAO.updateTugas(tugas)
    }
}