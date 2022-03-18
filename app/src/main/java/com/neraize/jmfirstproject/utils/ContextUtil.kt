package com.neraize.jmfirstproject.utils

import android.content.Context

//■변수단위의 데이터 반영구 저장 - SharedPreferences■
//(1) 개요
//- 지금까지 우리가 만든 앱은, 앱이 종료되는 순간 앱을 사용하면서 생성된
//모든 데이터를 잃어버림
//-SAVE 기능이 없는 앱 (RAM에만 데이터기록, 보조기억장치 활용X)
//
//- 앱종료 / 전원종료 여부와 관계없이, 간단한 데이터를 (반)영구적으로 보관
//하고 싶을때 이용하는 클래스
//
//- 간단한 데이터 : 변수단위로 데이터를 저장(set) / 불러내기(get)

class ContextUtil {

    companion object{

        private val prefName = "jmFirstProject"

        private  val LOGIN_USER_TOKEN ="LOGIN_USER_TOKEN"

        fun setLoginUserToken(context:Context, token:String){

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(LOGIN_USER_TOKEN, token).apply()
        }

        fun getLoginUserToken(context: Context): String{
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return  pref.getString(LOGIN_USER_TOKEN, "")!!
        }
    }
}
