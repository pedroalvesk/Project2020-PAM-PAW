package com.example.myapplication.dataBase

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.HashMap

@Suppress("JAVA_CLASS_ON_COMPANION")
class LoaderContentDatabase {

    companion object Factory {
        private lateinit var userInvoiceDao: UserInvoiceDao
        //private lateinit var customerTaskDao: CustomerTaskDao
        //private lateinit var customerTaskDetailViewDao: CustomerTaskDetailViewDao

        private lateinit var db: DataBase //Database

        val COUNT = 19

        private var _CUSTOMER_ITEMS: MutableList<CustomerItem> = ArrayList()
        val CUSTOMER_ITEMS: MutableList<CustomerItem>
            get() {
                /*if (_CUSTOMER_ITEMS == null) {
                    _CUSTOMER_ITEMS = ArrayList() // Type parameters are inferred
                }*/
                return _CUSTOMER_ITEMS
            }

        private var _CUSTOMER_ITEM_MAP: MutableMap<String, CustomerItem> = HashMap()
        val CUSTOMER_ITEM_MAP: MutableMap<String, CustomerItem>
            get() {
                /*if (_CUSTOMER_ITEM_MAP == null) {
                    _CUSTOMER_ITEM_MAP = HashMap() // Type parameters are inferred
                }*/
                return _CUSTOMER_ITEM_MAP
            }

        fun getCustomerDao() : UserInvoiceDao{
            return userInvoiceDao
        }

        /**
         * Create the database by getting its INSTANCE
         */
        fun createDb(context: Context) {
            Log.e(
                this.javaClass.simpleName,
                "createDb(): going to create DB and get userDao..."
            )
            //Create a version of the DB
            db = DataBase.getUserDatabaseInstance(context)
            userInvoiceDao = db.userDao()
        }

        /**
         * Close database
         */
        fun closeDb() {
            Log.e(this.javaClass.simpleName, "closeDb(): going to close DB...")
            db.close()
        }

        /**
         * Insert some data into database for presenting into CustomerItemContactDetailListActivity
         */
        fun addSampleItemsToDatabase() {
            // Create and Insert some sample Customers.
            for (i in 1..COUNT) {
                //CREATE
                val user: UserInvoice =
                    UserInvoice(
                        i, "User $i",
                        1,
                    )
                Log.e(
                    this.javaClass.simpleName,
                    "addSampleItemsToDatabase(): create customer = $user"
                )
                Log.e(
                    this.javaClass.simpleName,
                    "BEFORE INSERT"
                )
                //INSERT
                val id: Long = userInvoiceDao.insertUserInvoice(user)
                Log.e(
                    this.javaClass.simpleName,
                    "addSampleItemsToDatabase(): added record id = $id"
                )
            }
            //Query for all Customers
            val allCustomers = userInvoiceDao.loadAllUserInvoice()
            Log.e(
                this.javaClass.simpleName,
                "testeInsertCustomersAndListInserted(): allCustomers.size = ${allCustomers.iterator()}"
            )
            //Populate CUSTOMER_ITEMS and CUSTOMER_ITEM_MAP with all Customers
            for (c: UserInvoice in allCustomers.iterator()!!) {
                addItem(createCostumerItem(c))
            }
        }

        private fun addItem(item: CustomerItem) {
            CUSTOMER_ITEMS.add(item)
            CUSTOMER_ITEM_MAP.put(item.id, item)
        }

        private fun createCostumerItem(c: UserInvoice): CustomerItem {
            return CustomerItem("${c.userID}", c.userInvoiceFilename, makeDetails(c))
        }

        private fun makeDetails(c: UserInvoice): String {
            val builder = StringBuilder()
            builder.append(c.userID).append("\n")
            builder.append(c.userInvoiceFilename).append("\n")

            return builder.toString()
        }
    }

    /**
     * A CustomerItem item representing a piece of content from Customer.
     */
    data class CustomerItem(val id: String, val content: String?, val details: String) {
        override fun toString(): String = if (content!=null) content else ""
    }


}