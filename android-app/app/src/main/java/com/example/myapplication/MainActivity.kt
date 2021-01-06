package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to all views
        val et_user_name = findViewById<EditText>(R.id.et_user_name)
        val et_password = findViewById<EditText>(R.id.et_password)

        val btn_reset = findViewById<Button>(R.id.btn_reset)
        val btn_submit = findViewById<Button>(R.id.btn_submit)

        // clearing user_name and password edit text views on reset button click
        btn_reset.setOnClickListener {
            //et_user_name.setText("")
            //et_password.setText("")

            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val username = et_user_name.text
        val password = et_password.text

        val url = "http://10.100.54.241:8090/api/v1/auth/login"

        // set on-click listener
        btn_submit.setOnClickListener {

            val username = et_user_name.text
            val password = et_password.text

            val url = "http://10.100.14.168:8090/api/v1/auth/login"


            val data = JSONObject()
            try {
                //input your API parameters
                data.put("username", username)
                data.put("password", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, data,
                    { response ->
                        setContentView(R.layout.activity_main2)

                        // Change
                        val intent = Intent(this, MainActivity2::class.java)
                        startActivity(intent)
                    },
                    { error ->
                        // TODO: Handle error
                    }
            )
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(jsonObjectRequest)
        }
    }
}

