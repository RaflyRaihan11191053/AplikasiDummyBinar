package com.example.aplikasidummybinar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.aplikasidummybinar.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegis.setOnClickListener {
            if (binding.etEmailRegister.text.isNullOrEmpty() && binding.etUsernameRegister.text.isNullOrEmpty() && binding.etPasswordRegister.text.isNullOrEmpty()) {
                Toast.makeText(this@RegisterActivity, "Form regitrasi belum terisi, harap isi terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else if (binding.etUsernameRegister.text.isNullOrEmpty()) {
                binding.etUsernameRegister.error = "Tentukan Username kamu terlebih dahulu"
            } else if (binding.etEmailRegister.text.isNullOrEmpty()) {
                binding.etEmailRegister.error = "Masukkan Email yang akan kamu pakai"
            } else if (binding.etPasswordRegister.text.isNullOrEmpty()) {
                binding.etPasswordRegister.error = "Tentukan Password kamu terlebih dahulu"
            } else {
                ApiClient.instance.register(register = RegisterRequest("example@gmail.com", "person", "pass"))
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            val body = response.body()
                            val code = response.code()
                            if (code == 201) {
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@RegisterActivity, "Gagal Registrasi", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(this@RegisterActivity, "Gagal Registrasi", Toast.LENGTH_SHORT).show()
                        }
                    })

            }

        }

    }
}