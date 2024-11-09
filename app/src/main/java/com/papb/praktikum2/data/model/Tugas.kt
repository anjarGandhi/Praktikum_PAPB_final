package com.papb.praktikum2.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tugas")
@Parcelize
data class Tugas(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "matkul")
    var matkul: String,

    @ColumnInfo(name = "detail_tugas")
    var detail_tugas: String,

    @ColumnInfo(name = "selesai")
    var selesai: Boolean = false,

    @ColumnInfo(name = "image_uri")
    var imageUri: String? = null
) : Parcelable