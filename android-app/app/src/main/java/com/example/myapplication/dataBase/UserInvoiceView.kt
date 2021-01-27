package com.example.myapplication.dataBase

import androidx.room.DatabaseView


@DatabaseView("SELECT userInvoice.UserInvoiceID "+
        " FROM userInvoice " )
data class UserInvoiceView (
    val UserInvoiceID: Int,


)