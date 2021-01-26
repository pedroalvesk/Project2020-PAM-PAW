package com.example.myapplication.dataBase

import android.util.Log
import androidx.annotation.WorkerThread

/**
 * A repository class abstracts access to multiple data sources and provides Façade for the app to
 * access those data sources. The repository is not part of the Architecture Components libraries,
 * but a suggested best practice for code separation.
 *
 * The Repository manages queries and allows to use multiple backends, e.g. it implements logic for
 * fetching data from a network or use results cached in a local database.
 *
 * Pass in the DAO (as a private property in the constructor) instead of the whole database.
 */
class UserInvoiceRepository(private val userDao: UserInvoiceDao) {

    // Room executes all queries on a separate thread.
    // The public property is an observable LiveData which notifies  observer when  data changes.
    val allUsers: Array<UserInvoice> = userDao.loadAllUserInvoice()

    // Make this a suspend function so the caller knows this must be called on a non-UI thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user: UserInvoice) {
        Log.e(
            this.javaClass.simpleName,
            "insertCustomer(): going to insert new customer ${user}"
        )
        userDao.insertUserInvoice(user)
    }
}
