package com.example.myapplication

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.dataBase.LoaderContentDatabase
import com.example.myapplication.dataBase.UserInvoice
import com.example.myapplication.viewModel.UserInvoiceViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import java.lang.ref.WeakReference

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItemListActivity].
 */
class ItemDetailActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserInvoiceViewModel

    //Code for communication between activities
    private val newCustomerActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))


       // LoaderContentDatabase.createDb(this)
       // LoaderContentDatabase.addSampleItemsToDatabase()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val url = "http://192.168.56.1:8090/api/v1/backoffice/invoices"

        // Get new or existing CustomersViewModel from ViewModelProvider
        userViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
            ).get(UserInvoiceViewModel::class.java)

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONObject("message")
                for (i in 0 until jsonArray.length()) {
                    val invoices = jsonArray.getJSONObject(i.toString())

                    //val mail = jsonArray.getJSONObject(1)
                    val user = UserInvoice(0, "", 0);
                    userViewModel.insert(user)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        ItemDetailFragment.ARG_ITEM_ID,
                        intent.getStringExtra(ItemDetailFragment.ARG_ITEM_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {

                navigateUpTo(Intent(this, ItemListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {

        /**
         * class SomeTask : AsyncTask<Params, Progress, Result>
         *      Params: type of the parameters sent to doInBackground()
         *      Progress: type of the progress parameter sent to onProgressUpdate()
         *      Result: type of result of the background computation passed to onPostExecute().
         */
        class CustomersDatabaseAsyncTask(activity: ItemDetailActivity) :
            AsyncTask<String, Int, String>() {

            //private val parentActivity: CustomerItemContactDetailListActivity = activity
            private val activityReference: WeakReference<ItemDetailActivity> =
                WeakReference(activity)

            /**
             * Invoked on the UI thread before the task is executed.
             */
            override fun onPreExecute() {
                super.onPreExecute()
                Log.e(
                    this.javaClass.simpleName,
                    "onPreExecute(): going to do something before create CustomersDatabase..."
                )
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                //...
            }

            /**
             * invoked on the background thread immediately after onPreExecute().
             */
            override fun doInBackground(vararg params: String?): String {
                Log.e(
                    this.javaClass.simpleName,
                    "doInBackground(): going to create CustomersDatabase, params = ${params[0]}"
                )
                val parentActivity = activityReference.get()
                Log.e(
                    this.javaClass.simpleName,
                    "doInBackground(): LoaderCustomersContentDatabase.CUSTOMER_ITEMS.size = ${LoaderContentDatabase.CUSTOMER_ITEMS.size}"
                )
                if (parentActivity != null && LoaderContentDatabase.CUSTOMER_ITEMS.size == 0) {

                    //val context: Context = InstrumentationRegistry.getTargetContext()
                    LoaderContentDatabase.createDb(parentActivity.applicationContext)
                    this.publishProgress(50)
                    LoaderContentDatabase.addSampleItemsToDatabase()
                    this.publishProgress(100)
                }
                return "OK"
            }

            /**
             * invoked on the UI thread after a call to publishProgress().
             */
            override fun onProgressUpdate(vararg progress: Int?) {
                val parentActivity = activityReference.get()
                if (parentActivity != null) {
                    Log.e(
                        this.javaClass.simpleName,
                        "onProgressUpdate(): Progress: ${progress[0]}%"
                    )
                    Toast.makeText(parentActivity, "Progress: ${progress[0]}%", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            /**
             * invoked on the UI thread after the background computation finishes.
             */
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.e(
                    this.javaClass.simpleName,
                    "onPostExecute(): after creating CustomersDatabase, result=$result"
                )
                val parentActivity = activityReference.get()
                if (parentActivity != null && result == "OK") {
                    //parentActivity.setupRecyclerView(parentActivity.itemcontactdetail_list)
                    Toast.makeText(parentActivity, "Customers.db... Done!!", Toast.LENGTH_LONG)
                        .show()
                    parentActivity.startFollowingActivity()
                }
            }
        }
    }
    protected fun startFollowingActivity() {
        //Call CustomerItemContactDetailListActivity to list dababase content
        val intent = Intent(this, ItemDetailActivity::class.java)
        //Pass any data to next activity
        intent.putExtra("CUSTOMER_ITEMS_SIZE", LoaderContentDatabase.CUSTOMER_ITEMS.size)
        //Start your next activity
        startActivity(intent)
    }
}