package com.example.myapplication.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "userInvoice",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("userID"),
        childColumns = arrayOf("userID")
    )],
    indices = [androidx.room.Index(value = ["userID"])]
)
data class UserInvoice(
    @PrimaryKey
    @ColumnInfo(name = "UserInvoiceID") var UserInvoiceID: Int,
    @ColumnInfo(name = "userInvoiceFilename") var userInvoiceFilename: String?,
    @ColumnInfo(name = "userID") var userID: Int
)