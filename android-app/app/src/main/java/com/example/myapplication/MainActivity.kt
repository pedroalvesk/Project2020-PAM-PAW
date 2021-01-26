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

        btn_reset.setOnClickListener {

            setContentView(R.layout.activity_register)

            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        btn_submit.setOnClickListener {

            val username = et_user_name.text
            val password = et_password.text

            val url = "http://192.168.56.1:8090/api/v1/auth/login"


            val data = JSONObject()
            try {
                data.put("username", username)
                data.put("password", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, data,
                { response ->
                    setContentView(R.layout.item_list)
                    val intent = Intent(this, ItemListActivity::class.java)
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

