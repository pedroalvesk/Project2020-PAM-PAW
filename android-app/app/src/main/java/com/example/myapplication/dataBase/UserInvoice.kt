package com.example.myapplication.dataBase

import androidx.room.*

@Entity(
    tableName = "userInvoice",
    indices = [androidx.room.Index(value = ["userID"])]
)
data class UserInvoice(

    @PrimaryKey
    @ColumnInfo(name = "UserInvoiceID") var UserInvoiceID: Int,
    @ColumnInfo(name = "userInvoiceFilename") var userInvoiceFilename: String? = "",
    @ColumnInfo(name = "userID") var userID: Int
    //@ColumnInfo(name = "userToken") var userToken: String? = "",

)
{
    constructor() : this(0, "", 0 )
}