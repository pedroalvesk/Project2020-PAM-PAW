package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
<<<<<<< HEAD
        
=======
        onPostExecute()
>>>>>>> 6fd2f7ea5f9daba39d4196806f179247c3169036

    }

    fun onPostExecute() {
        val textView = findViewById<TextView>(R.id.textView1)
        val requestQueue = Volley.newRequestQueue(this)

        val url = "http://10.100.14.168:8090/api/v1/invoices"

        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOjAsInVzZXJuYW1lIjoiYWRtaW4iLCJleHAiOjE2MDk3MDkwMDF9.g6mZR0RbgJOG49ZQ8SEsnrYbHcI2i5RUkgqGoYV-dbk";
        val data = JSONObject()
        try {
            //input your API parameters
            data.put("token", token)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, data,
            { response ->
                textView.append("SUCCESS\n\n")
            },
            { error ->
                textView.append("EROOR\n\n")
                // TODO: Handle error
            }
        )

        /*

        val request =
            JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("message")
                        for (i in 0 until jsonArray.length()) {
                            val invoices = jsonArray.getJSONObject(i)
                            val extension = invoices.getString("extension")
                            val filename = invoices.getString("filename")

                            textView.append(" $filename, $extension\n\n")
                            //val str: String = textView.text.toString()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, { error -> error.printStackTrace() })

         */
        requestQueue.add(jsonObjectRequest)
    }

}