package com.neraize.jmfirstproject.api

import android.util.Log
import com.google.firebase.database.*
import com.neraize.jmfirstproject.MainActivity
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
                        // for(i in 0 .. snapshotSize){
                        for(item in snapshot.child(mUserIdReplaceDotToStar).children){
                            val pushCountry = item.child("push_country").value.toString()
                            val possibility = item.child("possibility").value.toString()

                            if(pushCountry=="null"){
                                snapshotLastNum = snapshot.child(mUserIdReplaceDotToStar).children.last().key!!.toLong()!!
                                break
                            }
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
    }
}