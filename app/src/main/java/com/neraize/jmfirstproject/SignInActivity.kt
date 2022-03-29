package com.neraize.jmfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.databinding.ActivitySignInBinding
import com.neraize.jmfirstproject.datas.BasicResponse
import com.neraize.jmfirstproject.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : BaseActivity() {

    lateinit var binding:ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

        // 회원가입
        binding.btnSignUp.setOnClickListener {

            val myIntent = Intent(mContextt, SignUpActivity::class.java)
            startActivity(myIntent)
        }


        // 로그인
        binding.btnLogin.setOnClickListener {

            val inputId = binding.edtId.text.toString()
            val inputPassword = binding.edtPassword.text.toString()

            apiList.postRequestLogin(inputId, inputPassword).enqueue(object :Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                    if(response.isSuccessful){
                        var br =  response.body()!!
                        Toast.makeText(mContextt, "${br.data.user.nick_name}님, 환영합니다", Toast.LENGTH_SHORT).show()

                        ContextUtil.setLoginUserToken(mContextt, br.data.token)

                        val myIntent = Intent(mContextt, MainActivity::class.java)
                        startActivity(myIntent)

                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

        //  체크박스의 체크여부가 변경되면 -> ContextUtil이용하여 체크값 저장
         binding.ckboxAutoLogin.setOnCheckedChangeListener { compoundButton, isChecked ->

             // Log.d("체크값변경", "${ isChecked}로 변경")
             ContextUtil.setAutoLogin(mContextt, isChecked)
         }
    }

    override fun SetValues() {

        txtProfile.visibility =View.GONE

        // 자동로그인 체크박스 체크여부 반영
        binding.ckboxAutoLogin.isChecked = ContextUtil.getAutoLogin(mContextt)
    }
}