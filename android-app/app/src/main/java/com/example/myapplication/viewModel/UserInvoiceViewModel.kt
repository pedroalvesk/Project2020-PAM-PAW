package com.example.myapplication.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dataBase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInvoiceViewModel (app: Application) : AndroidViewModel(app) {

    private val repository: UserInvoiceRepository

    var allUsers: Array<UserInvoice>

    init {
        val userDao =
            DataBase.getUserDatabaseInstance(app, viewModelScope).userDao()
        repository = UserInvoiceRepository(userDao)
        allUsers = repository.allUsers
    }

    /** Launch new (non-blocking) coroutine to insert a customer */
    fun insert(user: UserInvoice) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(this.javaClass.simpleName,
                "launch(): async insert new customer ${user}"
            )
            repository.insertUser(user)
        }

    private val users: MutableLiveData<List<UserInvoice>> by lazy {
        MutableLiveData<List<UserInvoice>>().also {
            loadUsers()
        }
    }

    fun getUser(id : Int): UserInvoice {

        return UserInvoice();
    }

    /** Do an asynchronous operation to fetch customers */
    private fun loadUsers() {
        Log.e(
            this.javaClass.simpleName,
            "loadUsers(): going to load customer to DB..."
        )

        viewModelScope.launch(Dispatchers.IO) {
            Log.e(this.javaClass.simpleName,
                "launch(): loading all customers..."
            )
            val customerDao: UserInvoiceDao = LoaderContentDatabase.getCustomerDao()
            allUsers = customerDao.loadAllUserInvoice()
        }
    }
}