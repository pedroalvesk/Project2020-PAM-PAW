package com.example.myapplication.dataBase

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
    fun loadAllUsers(): LiveData<List<User>>

    /**
     * Get a user by id.
     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM users WHERE userID = :id LIMIT 1")
    fun getuserById(id: String): User


    /**
     * Insert a user in the database (returns id), replace it if already exists.
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long


    /**
     * Insert 1+ customers into database. If the customers already exists, replace them.
     * @param users the set of customers to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: User): List<Long>

    /**
     * Update 1+ customers into database (returns number of updates rows).
     * @param users the set of customers to be updated.
     */
    @Update
    fun updateUsers(vararg users: User): Int

    /**
     * Delete 1+ customers from database (returns number of deleted rows).
     * @param users the set of customers to be deleted.
     */
    @Delete
    fun deleteUsers(vararg users: User): Int

    /**
     * Delete all users.
     */
    @Query("DELETE FROM users")
    fun deleteAllUsers(): Int

}