package com.example.myapplication.dataBase
import androidx.room.*

@Dao
interface UserInvoiceDao {

    /**
     * Get all userInvoices.
     * @return all userInvoices from the table.
     */
    @Query("SELECT * FROM userInvoice")
    fun loadAllUserInvoice(): Array<UserInvoice>

    /**
     * Insert a userInvoice in the database (returns id), replace it if already exists.
     * @param userInvoice the userInvoice to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInvoice(userInvoice: UserInvoice): Long

    /**
     * Delete 1+ userInvoice from database (returns number of deleted rows).
     * @param userInvoices the set of userInvoice to be deleted.
     */
    @Delete
    fun deleteUserInvoices(vararg userInvoices: UserInvoice): Int

    /**
     * Delete all userInvoice.
     */
    @Query("DELETE FROM userInvoice")
    fun deleteAllUserInvoices(): Int
}