package com.neraize.jmfirstproject

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.databinding.ActivitySignUpBinding
import com.neraize.jmfirstproject.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {


        // 아이디 중복확인 및 유효성검사
        binding.btnIdDupCheck.setOnClickListener {

            val inputId = binding.edtId.text.toString()

            val pattern:Pattern = Patterns.EMAIL_ADDRESS

            if(pattern.matcher(inputId).matches()){

                apiList.getRequestDuplicateCheck("EMAIL", inputId).enqueue(object :Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(mContextt, "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show()
                            binding.btnIdDupCheck.text = "사용가능"

                        }
                        else{
                            Toast.makeText(mContextt, "중복된 아이디입니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
            else{
                Toast.makeText(mContextt, "잘못된 이메일형식입니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        // 내용이 바뀌면 중복확인 재요청
        binding.edtId.addTextChangedListener {
            binding.btnIdDupCheck.text = "중복확인"
        }


        // 회원가입
        binding.btnSignUp.setOnClickListener {

            val inputId = binding.edtId.text.toString()
            val inputPassword = binding.edtPassword.text.toString()
            val inputPasswordCheck = binding.edtPasswordCheck.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

            // 비번중복체크
            if(inputPassword != inputPasswordCheck){
                Toast.makeText(mContextt, "비밀번호가 일치하지않습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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

        txtProfile.visibility = View.GONE
    }
}