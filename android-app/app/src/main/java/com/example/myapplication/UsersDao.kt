package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface UsersDao {

    /**
     * Get all users.
     * @return all users from the table.
     */
    @Query("SELECT * FROM users")
    fun loadAllCustomers(): LiveData<List<User>>

    /**
     * Get a user by id.
     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM users WHERE userID = :id LIMIT 1")
    fun getCustomerById(id: String): User


    /**
     * Insert a user in the database (returns id), replace it if already exists.
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: User): Long

    /**
     * Insert a user in the database (returns id), replace it if already exists.
     * @param user the customer to be inserted.
     */
/*    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomerCompletable(customer: User): Completable*/

    /**
     * Insert 1+ customers into database. If the customers already exists, replace them.
     * @param customers the set of customers to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomers(vararg customers: User): List<Long>

    /**
     * Update 1+ customers into database (returns number of updates rows).
     * @param customers the set of customers to be updated.
     */
    @Update
    fun updateCustomers(vararg customers: User): Int

    /**
     * Delete 1+ customers from database (returns number of deleted rows).
     * @param customers the set of customers to be deleted.
     */
    @Delete
    fun deleteCustomers(vararg customers: User): Int

    /**
     * Delete all customers.
     */
    @Query("DELETE FROM users")
    fun deleteAllcustomers(): Int

    /**
     * Get all customers minimal info.
     * @return the customers from the table with specific cities.
     */
/*    @Query("SELECT customername, customercompany, customerID FROM customersMinimal")
    fun loadFullCustomerMinimalInfo(): List<CustomerMinimal>*/
}