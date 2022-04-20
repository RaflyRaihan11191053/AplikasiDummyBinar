package com.example.aplikasidummybinar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aplikasidummybinar.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    
    lateinit var binding: ActivityLoginBinding

    lateinit var sharedPreferences: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            if (binding.etEmailLogin.text.isNullOrEmpty() && binding.etPasswordLogin.text.isNullOrEmpty()) {
                Toast.makeText(this@LoginActivity, "Form regitrasi belum terisi, harap isi terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else if (binding.etEmailLogin.text.isNullOrEmpty()) {
                binding.etEmailLogin.error = "Masukkan Email kamu yang terdaftar"
            } else if (binding.etPasswordLogin.text.isNullOrEmpty()) {
                binding.etPasswordLogin.error = "Masukkan Password kamu terlebih dahulu"
            } else {
                ApiClient.instance.login(login = LoginRequest("example@gmail.com", "pass"))
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            val body = response.body()
                            val code = response.code()
                            if (code == 200) {
                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                editor.putString("username", binding.etEmailLogin.text.toString())
                                editor.putString("token", body?.data?.token)
                                editor.apply()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@LoginActivity, "Gagal Registrasi", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "Gagal Registrasi", Toast.LENGTH_SHORT).show()
                        }
                    })

            }

        }

        binding.btnGoRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }
}