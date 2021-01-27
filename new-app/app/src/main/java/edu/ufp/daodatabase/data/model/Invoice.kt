package edu.ufp.daodatabase.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "invoice_table")
data class Invoice(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val filename: String,
    val processed: Boolean,
    val data: String,
    val type: String
): Parcelable