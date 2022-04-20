package com.example.aplikasidummybinar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aplikasidummybinar.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)

        binding.btnAuth.setOnClickListener {
            getAuth()
        }
    }

    fun getAuth() {
        val authToken = sharedPreferences.getString("token", null)
        ApiClient.instance.auth("Bearer $authToken")
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        Toast.makeText(this@MainActivity, "Token valid", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Token tidak valid", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Token tidak valid", Toast.LENGTH_SHORT).show()
                }
            })
    }
}