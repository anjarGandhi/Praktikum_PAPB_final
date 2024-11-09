package com.papb.praktikum2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papb.praktikum2.data.model.Tugas
import com.papb.praktikum2.data.model.TugasRepository
import kotlinx.coroutines.launch

class MainViewModel(private val tugasRepository: TugasRepository) : ViewModel() {
    val tugasList = tugasRepository.getAllTugas()

    fun addTugas(matkul: String, detailTugas: String, imageUri: String? = null) {
        viewModelScope.launch {
            val newTugas = Tugas(
                matkul = matkul,
                detail_tugas = detailTugas,
                imageUri = imageUri,
                selesai = false
            )
            tugasRepository.insert(newTugas)
        }
    }

    fun updateTugasStatus(tugas: Tugas) {
        viewModelScope.launch {
            tugasRepository.update(tugas)
        }
    }


    fun updateTugasWithImage(tugas: Tugas, newImageUri: String) {
        viewModelScope.launch {
            val updatedTugas = tugas.copy(imageUri = newImageUri)
            tugasRepository.update(updatedTugas)
        }
    }
}