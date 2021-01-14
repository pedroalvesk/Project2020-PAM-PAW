package com.example.myapplication.Database

import android.graphics.Bitmap
import androidx.room.*

@Entity(
    tableName = "invoice",
    indices = [Index(value = ["userID"])]
)
data class Invoice(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID") var userID: Int = 0,
    @ColumnInfo(name = "filename") var filename: String? = "",
    @ColumnInfo(name = "extension") var extension: String? = "",

    @Ignore var picture: Bitmap? = null
)
