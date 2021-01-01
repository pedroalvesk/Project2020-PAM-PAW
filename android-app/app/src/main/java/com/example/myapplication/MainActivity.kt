package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import java.util.logging.Level.INFO
import com.android.volley.toolbox.StringRequest as StringRequest

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to all views
        val et_user_name = findViewById(R.id.et_user_name) as EditText
        val et_password = findViewById(R.id.et_password) as EditText

        val btn_reset = findViewById(R.id.btn_reset) as Button
        val btn_submit = findViewById(R.id.btn_submit) as Button

        // clearing user_name and password edit text views on reset button click
        btn_reset.setOnClickListener {
            et_user_name.setText("")
            et_password.setText("")
        }

        // set on-click listener
        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val password = et_password.text;

            Toast.makeText(this@MainActivity, user_name, Toast.LENGTH_LONG).show()

            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.1.150:8081/api/v1/auth/login"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(Request.Method.POST, url,
                { response ->
                    // Display the first 500 characters of the response string.
                    Log.e(localClassName, "Response:$response")
                    et_user_name.setText(response)
                },
                { error ->
                    Log.e(localClassName, "Error on volley:$error")
                })

                queue.add(stringRequest)
        }



    }
}