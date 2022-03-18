package com.neraize.jmfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.databinding.ActivitySignUpBinding
import com.neraize.jmfirstproject.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

        // 아이디 중복확인
        binding.btnIdDupCheck.setOnClickListener {


        }

        // 회원가입
        binding.btnSignUp.setOnClickListener {

            val inputId = binding.edtId.text.toString()
            val inputPassword = binding.edtPassword.text.toString()
            val inputPasswordCheck = binding.edtPasswordCheck.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

            apiList.postRequestSignUp(inputId, inputPassword, inputNickname).enqueue(object :Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(mContextt, "회원가입에 성공하였습니다", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })
        }
    }

    override fun SetValues() {

    }
}