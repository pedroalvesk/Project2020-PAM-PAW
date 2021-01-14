package com.example.myapplication

import android.graphics.Bitmap
import androidx.room.*

@Entity(
    tableName = "users",
    indices = [Index(value = ["userID"])]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID") var userID: Int = 0,
    @ColumnInfo(name = "userToken") var userToken: String? = "",

    @Ignore var picture: Bitmap? = null
)
