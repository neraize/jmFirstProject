package com.neraize.jmfirstproject.api

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.database.*
import com.neraize.jmfirstproject.AdministratorActivity
import com.neraize.jmfirstproject.MainActivity
import com.neraize.jmfirstproject.SplashActivity
import com.neraize.jmfirstproject.adapers.MyAdminListAdapter
import com.neraize.jmfirstproject.datas.MyAlarmData


class FirebaseDbConnect {


    companion object{

        var snapshotLastNum =0L
        var snapshotSize =0L

        // 디비중에서, 로그인한 이메일주소 기준으로 알람추가한 국가리스트 찾기
        fun getMyAlarmList(mUserIdReplaceDotToStar:String) : ArrayList<MyAlarmData>{
            // Log.d("mUserIdReplaceDotToStar", mUserIdReplaceDotToStar.toString())

            // 파이어베이스 디비 연결
            val database = FirebaseDatabase.getInstance()

            // 디비중에서, 로그인한 이메일주소 기준으로 알람추가한 국가리스트 찾기
            val myRef = database.getReference("alarm")

            myRef.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    snapshotSize = snapshot.child(mUserIdReplaceDotToStar).childrenCount

                    Log.d("snapshotSize", snapshotSize.toString())

                    if(snapshot.hasChild(mUserIdReplaceDotToStar)){
                        MainActivity.mAlarmList.clear()
                        // for(i in 0 .. snapshotSize){
                        for(item in snapshot.child(mUserIdReplaceDotToStar).children){
                            val pushCountry = item.child("push_country").value.toString()
                            val possibility = item.child("possibility").value.toString()

                            snapshotLastNum = snapshot.child(mUserIdReplaceDotToStar).children.last().key!!.toLong()!!
//                            if(pushCountry=="null"){
//                                snapshotLastNum = snapshot.child(mUserIdReplaceDotToStar).children.last().key!!.toLong()!!
//                                break
//                            }
                            //Log.d("알림${i}",pushCountry)
                            MainActivity.mAlarmList.add(MyAlarmData(pushCountry, possibility))
                            Log.d("pushCountry", pushCountry.toString())
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
            return MainActivity.mAlarmList
        }


        // 디비중에서, 로그인한 이메일주소 기준으로 알람국가  추가/삭제기능
        fun setMyAlarmList(mUserIdReplaceDotToStar:String, selectedCountry:String, possibility:String ,isAlarmSet:Boolean){
            // Log.d("mUserIdReplaceDotToStar", mUserIdReplaceDotToStar.toString())
            Log.d("setMyAlarmList함수진입", "snapshotLastNum${snapshotLastNum}")

            var isAlarmSetForReturn =isAlarmSet

            // 파이어베이스 디비 연결
            val database = FirebaseDatabase.getInstance()

            // 디비중에서, 로그인한 이메일주소 기준으로 알람추가한 국가리스트 찾기
            val myRef = database.getReference("alarm")

            // 해당국가의 알람이 있었던 경우  ->  삭제 함수
            fun deleteAlarm(snapshotNum:Int){
                Log.d("삭제함수진입", "snapshotLastNum: ${snapshotNum}")
                myRef.child(mUserIdReplaceDotToStar).child(snapshotNum.toString()).child("push_country").parent!!.removeValue()

                for (alarm in ArrayList<MyAlarmData>(MainActivity.mAlarmList)) {
                    if(alarm.pushCountry == selectedCountry ){
                        MainActivity.mAlarmList.remove(alarm)
                        break
                    }
                }
            }

            // 해당국가의 알람이 없었던 경우  ->  추가 함수
            fun setAlarm(snapshotLastNum:Int){

                Log.d("추가함수진입", "snapshotLastNum${snapshotLastNum}")
                myRef.child(mUserIdReplaceDotToStar).child(snapshotLastNum.toString()).child("push_country").setValue(selectedCountry)
                myRef.child(mUserIdReplaceDotToStar).child(snapshotLastNum.toString()).child("possibility").setValue(possibility)
                isAlarmSetForReturn =true
                MainActivity.mAlarmList.clear()
                MainActivity.mAlarmList.add(MyAlarmData(selectedCountry, possibility))

                MainActivity.mAlarmList.forEach { alarm->
                    Log.d("FirebaseDbConnect", "알람추가후 리스트현황 ${alarm.pushCountry}")
                }
            }

            if(isAlarmSetForReturn){
                myRef.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        snapshotLastNum = snapshot.child(mUserIdReplaceDotToStar).children.last().key!!.toLong()!!
                        Log.d("FirebaseDbConnect", "snapshotLastNum: ${ snapshotLastNum.toString() }")

                        for(i in 0 .. snapshotLastNum){
                            val pushCountry = (snapshot.child(mUserIdReplaceDotToStar).child(i.toString()).child("push_country").value).toString()

                            if(pushCountry == selectedCountry){
                                //삭제함수 실행
                                deleteAlarm(i.toInt())
                                isAlarmSetForReturn=false
                                break
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            }
            else{
                //추가함수 실행
                setAlarm((snapshotLastNum.toInt())+1)
            }


        }



        // 관리자모드에서, country db 내용 변경
        fun setUpdateMyCountry(updateCountryName:String, possibility:String){

            // 업데이트함수용 국가 인덱스
            var snapshotNum = -1

            // 파이어베이스 디비 연결
            val database = FirebaseDatabase.getInstance()

            // 디비중에서, 로그인한 이메일주소 기준으로 알람추가한 국가리스트 찾기
            val myRef = database.getReference("country")

            // 추가 함수
            fun updateCountry(snapshotNum:Int){

                Log.d("업데이트함수진입", "snapshotLastNum${snapshotNum}")
                myRef.child(snapshotNum.toString()).child("possibility").setValue(possibility)

                SplashActivity.mCountryList.forEach { country->

                    if (country.name == updateCountryName){
                        country.possibility = possibility
                        Log.d("FirebaseDbConnect","mCountryList수정완료")
                        return@forEach
                    }
                }
                // 어댑터 새로고침ok
                AdministratorActivity.mAdapter.notifyDataSetChanged()

                //SplashActivity.getFireBaseDB()
            }

            myRef.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val snapshotSize = snapshot.childrenCount-1
                    Log.d("FirebaseDbConnect", "국가수: ${snapshotSize.toString()}")

                    for(i in 0 .. snapshotSize){
                        val name = (snapshot.child(i.toString()).child("name").value).toString()
                        val possibility = (snapshot.child(i.toString()).child("possibility").value).toString()

                        if(updateCountryName == name){
                            //업데이트함수용 인덱스받기
                            snapshotNum = i.toInt()
                            break
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) { }
            })

            val myHandler = Handler(Looper.getMainLooper())
            myHandler.postDelayed({
                if(snapshotNum !=-1){
                    updateCountry(snapshotNum)
                }
            },3000)
        }
    }
}