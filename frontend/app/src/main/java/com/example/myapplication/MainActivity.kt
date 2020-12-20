package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest as StringRequest

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to all views
        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var btn_reset = findViewById(R.id.btn_reset) as Button
        var btn_submit = findViewById(R.id.btn_submit) as Button

        btn_reset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            et_user_name.setText("")
            et_password.setText("")
        }

        // set on-click listener
        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val password = et_password.text;
            Toast.makeText(this@MainActivity, user_name, Toast.LENGTH_LONG).show()

            // your code to validate the user_name and password combination
            // and verify the same

            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "https://www.google.com"

            // Request a string response from the provided URL.
         /*   val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        // Display the first 500 characters of the response string.
                        et_user_name.text = "Response is: ${response.substring(0, 500)}"
                        et_password.text = "Response is: ${response.substring(0, 500)}"

                    },
                    Response.ErrorListener { et_user_name.text = "That didn't work!" })
                    Response.ErrorListener { et_password.text = "That didn't work!" })

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest)*/
            }
    }
}