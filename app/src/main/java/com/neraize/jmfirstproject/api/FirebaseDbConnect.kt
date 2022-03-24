package com.neraize.jmfirstproject.api

import android.util.Log
import com.google.firebase.database.*
import com.neraize.jmfirstproject.datas.MyAlarmData
import kotlin.math.log


class FirebaseDbConnect {


    companion object{

        var mAlarmList = ArrayList<MyAlarmData>()
        var snapshotLastNum =0L

        // 디비중에서, 로그인한 이메일주소 기준으로 알람추가한 국가리스트 찾기
        fun getMyAlarmList(mUserIdReplaceDotToStar:String) : ArrayList<MyAlarmData>{
            // Log.d("mUserIdReplaceDotToStar", mUserIdReplaceDotToStar.toString())

            // 파이어베이스 디비 연결
            val database = FirebaseDatabase.getInstance()

            // 디비중에서, 로그인한 이메일주소 기준으로 알람추가한 국가리스트 찾기
            val myRef = database.getReference("alarm")

            myRef.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val snapshotSize = snapshot.child(mUserIdReplaceDotToStar).childrenCount

                    Log.d("snapshotSize", snapshotSize.toString())

                    if(snapshot.hasChild(mUserIdReplaceDotToStar)){
                        for(i in 0 .. snapshotSize){
                            val pushCountry = (snapshot.child(mUserIdReplaceDotToStar).child(i.toString()).child("push_country").value).toString()

                            if(pushCountry=="null"){
                                snapshotLastNum = snapshot.child(mUserIdReplaceDotToStar).children.last().key!!.toLong()!!
                                break
                            }
                            //Log.d("알림${i}",pushCountry)
                            mAlarmList.add(MyAlarmData(pushCountry))
                            Log.d("pushCountry", pushCountry.toString())
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
            return mAlarmList
        }


        // 디비중에서, 로그인한 이메일주소 기준으로 알람국가  추가/삭제기능
        fun setMyAlarmList(mUserIdReplaceDotToStar:String, selectedCountry:String ,isAlarmSet:Boolean){
            // Log.d("mUserIdReplaceDotToStar", mUserIdReplaceDotToStar.toString())
            Log.d("setMyAlarmList함수진입", "snapshotLastNum${snapshotLastNum}")

            var isAlarmSetForReturn =isAlarmSet

            // 파이어베이스 디비 연결
            val database = FirebaseDatabase.getInstance()

            // 디비중에서, 로그인한 이메일주소 기준으로 알람추가한 국가리스트 찾기
            val myRef = database.getReference("alarm")

            // 해당국가의 알람이 있었던 경우  ->  삭제 함수
            fun deleteAlarm(snapshotNum:Int){
                Log.d("삭제함수진입", "snapshotLastNum${snapshotLastNum}")
                myRef.child(mUserIdReplaceDotToStar).child(snapshotNum.toString()).removeValue()
            }

            // 해당국가의 알람이 없었던 경우  ->  추가 함수
            fun setAlarm(snapshotLastNum:Int){

                Log.d("추가함수진입", "snapshotLastNum${snapshotLastNum}")
                myRef.child(mUserIdReplaceDotToStar).child(snapshotLastNum.toString()).child("push_country").setValue(selectedCountry)
                isAlarmSetForReturn =true

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
                                mAlarmList.add(MyAlarmData(pushCountry))
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
//                myRef.addValueEventListener(object :ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//
//                        snapshotLastNum = snapshot.child(mUserIdReplaceDotToStar).children.last().key!!.toLong()!!
//                        Log.d("FirebaseDbConnect", "snapshotLastNum: ${ snapshotLastNum.toString() }")
//                    }
//                    override fun onCancelled(error: DatabaseError) {
//                    }
//                })
                setAlarm((snapshotLastNum.toInt())+1)
            }


        }
    }
}