package edu.ufp.daodatabase.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ufp.daodatabase.data.model.Invoice

/*
 * Contains all the methods used for accessing the database.
 */
@Dao
interface InvoiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addInvoice(invoice: Invoice)

    @Query("SELECT * FROM invoice_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Invoice>>
}