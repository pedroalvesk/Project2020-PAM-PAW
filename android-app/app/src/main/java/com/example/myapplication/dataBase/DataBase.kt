package com.example.myapplication.dataBase

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.impl.WorkDatabaseMigrations.MIGRATION_1_2
import com.example.myapplication.viewModel.UserInvoiceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UserInvoice::class],
    views = [UserInvoiceView::class],
    version = 2
)
abstract class DataBase : RoomDatabase() {

    abstract fun userDao(): UserInvoiceDao

    //Behaves like a static attribute
    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getUserDatabaseInstance(context: Context): DataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DataBase::class.java,
                "User.db"
            )
                .fallbackToDestructiveMigration()
                //May use migration objets or each new schema
                .addMigrations(MIGRATION_1_2)
                .build()

        fun getUserDatabaseInstance(
            context: Context,
            scope: CoroutineScope
        ): DataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, scope).also { INSTANCE = it }
            }

        /** Populate DB through the use of a RoomDatabase.Callback use in Room.databaseBuilder(). */
        private fun buildDatabase(context: Context, scope: CoroutineScope) =
            Room.databaseBuilder(
                context.applicationContext,
                DataBase::class.java,
                "User.db"
            )
                .fallbackToDestructiveMigration()
                //Use migration objects for each new schema evolution
                .addMigrations(MIGRATION_1_2)
                //Use RoomDatabase.Callback() to clear and repopulate DB instead of migrating
                .addCallback(CustomersDatabaseCallback(scope))
                .build()
    }

    /**
     * The RoomDatabase.Callback() is called on DB databaseBuilder():
     *  1. override onOpen(): clear and repopulate DB whenever app is started;
     *  2. override the onCreate(): populate DB only the first time the app is launched.
     */
    private class CustomersDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        /** Override onOpen() to clear and populate DB every time app is started. */
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            // To keep DB data through app restarts comment coroutine exec:
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    cleanAndPopulateCustomersDatabase(database.userDao())
                }
            }
        }

        /** Overrite onCreate() to populate DB only first time app is launched. */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            //To clear and repopulate DB every time app is started comment coroutine exec:
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    //cleanAndPopulateCustomersDatabase(database.customerDao())
                }
            }
        }

        /**
         * Remove all customers from DB and populate with some customers.
         */
        fun cleanAndPopulateCustomersDatabase(customerDao: UserInvoiceDao) {
            // Clear all customers from DB
            customerDao.deleteUserInvoices()
            //Populate with some Patinhas customers
            for (i in 1..LoaderContentDatabase.COUNT) {
                //CREATE
                val user: UserInvoice =
                    UserInvoice(
                        i, "User $i", i,
                    )
                Log.e(
                    this.javaClass.simpleName,
                    "addSampleItemsToDatabase(): create customer = $user"
                )
                //INSERT
                val id: Long = customerDao.insertUserInvoice(user)
                Log.e(
                    this.javaClass.simpleName,
                    "addSampleItemsToDatabase(): added record id = $id"
                )
            }
        }
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE `userInvoice` (`id` INTEGER, `tasktitle` TEXT,`id` INTEGER, " +
                        "PRIMARY KEY(`id`))"
            )
        }
    }

}