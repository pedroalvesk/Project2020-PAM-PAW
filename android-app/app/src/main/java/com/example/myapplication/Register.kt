package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Register : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val user_name = findViewById<EditText>(R.id.user)
        val password = findViewById<EditText>(R.id.password)

        val submit = findViewById<Button>(R.id.btn_register)

        val name = user_name.text
        val paw = password.text

        val url = "http://192.168.1.150:8090/api/v1/auth/register"

        submit.setOnClickListener {
            val body = JSONObject()
            try {
                //input your API parameters
                body.put("username", name)
                body.put("password", paw)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, body,
                { response ->
                    setContentView(R.layout.activity_main)
                },
                { error ->
                    // TODO: Handle error
                }
            )
            val requestQueue1 = Volley.newRequestQueue(this)
            requestQueue1.add(jsonObjectRequest)
        }
    }
}