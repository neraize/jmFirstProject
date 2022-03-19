package com.neraize.jmfirstproject

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.neraize.jmfirstproject.api.APIList
import com.neraize.jmfirstproject.api.ServerAPI
import com.neraize.jmfirstproject.utils.ContextUtil

abstract class BaseActivity:AppCompatActivity() {

    lateinit var mContextt:Context

    // 모든화면에서, apiList변수가 있다면 => apiList.서버기능(  )형태로 간단히 코딩 가능
    lateinit var apiList : APIList

    // 액션바의 UI변수를 멤버변수로 -> 상속가능
    // 커스텀 액션바 생성뒤에 변수에 대입
    lateinit var txtLogOut:TextView
    lateinit var imgHome:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContextt = this

        if(supportActionBar !=null){
            setCustomActionBar()
        }

        val retrofit = ServerAPI.getRetrofit()
        apiList = retrofit.create(APIList::class.java)
    }

    abstract fun SetupEvents()
    abstract fun SetValues()

    fun setCustomActionBar(){
        // 커스텀 액션바
        val defaultActionBar = supportActionBar!!
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

        // 액션바 좌우 여백 제거
        val toolbar = defaultActionBar.customView.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0,0)

        // 홈이미지버튼
        imgHome = defaultActionBar.customView.findViewById(R.id.imgHome)
        imgHome.visibility = View.GONE

        // 로그아웃
        txtLogOut = defaultActionBar.customView.findViewById(R.id.txtLogOut)

        txtLogOut.visibility = View.GONE

        txtLogOut.setOnClickListener {
            val alert =  AlertDialog.Builder(mContextt)
                .setTitle("로그아웃")
                .setMessage("로그아웃을 하시겠습니까?")
                .setPositiveButton("확인",DialogInterface.OnClickListener { dialogInterface, i ->

                    ContextUtil.setLoginUserToken(mContextt, "")

                    val myIntent = Intent(mContextt, SplashActivity::class.java)

//                    FLAG_ACTIVITY_CLEAR_TASK
//                     이 Activity와 관련된 Task가 수행중이면 Activity가 수행되기 전에 다른 액티비티는 모두 종료된다.
//                     이 플래그는 FLAG_ACTIVITY_NEW_TASK와 같이 사용 되어야 한다.
//                    FLAG_ACTIVITY_NEW_TASK
//                     기전에 해당 태스크가 없을 때 새로운 task 를 만들면서 launch 시킨다.
//                     실행하고자 하는 Activity와 관련 태스크가 존재할 경우 해당 태스크가 Foreground로 오고 해당 태스크 내에서 Activity가 실행된다.
                    myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(myIntent)
                    finish()
                })
                .setNegativeButton("취소",null)
                .show()
        }
    }

}