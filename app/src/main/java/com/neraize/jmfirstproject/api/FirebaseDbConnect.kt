package com.neraize.jmfirstproject.api

import android.util.Log
import com.google.firebase.database.*
import com.neraize.jmfirstproject.datas.MyAlarmData
import kotlin.math.log


class FirebaseDbConnect {


    companion object{

        var mAlarmList = ArrayList<MyAlarmData>()


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

                    Log.d("snapshotSize1", snapshotSize.toString())

                    if(snapshot.hasChild(mUserIdReplaceDotToStar)){
                        for(i in 0 .. snapshotSize){
                            val pushCountry = (snapshot.child(mUserIdReplaceDotToStar).child(i.toString()).child("push_country").value).toString()

                            if(pushCountry=="null"){
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
        fun setMyAlarmList(mUserIdReplaceDotToStar:String, selectedCountry:String ,isAlarmSet:Boolean) {
            // Log.d("mUserIdReplaceDotToStar", mUserIdReplaceDotToStar.toString())

            // 파이어베이스 디비 연결
            val database = FirebaseDatabase.getInstance()

            // 디비중에서, 로그인한 이메일주소 기준으로 알람추가한 국가리스트 찾기
            val myRef = database.getReference("alarm")

            // 해당국가의 알람이 있었던 경우  ->  삭제 함수
            fun deleteAlarm(snapshotSize:Int){
                myRef.child(mUserIdReplaceDotToStar).child(snapshotSize.toString()).removeValue()
            }

            // 해당국가의 알람이 없었던 경우  ->  추가 함수
            fun setAlarm(snapshotSize:Int){
                myRef.child(mUserIdReplaceDotToStar).child(snapshotSize.toString()).child("push_country").setValue(selectedCountry)
            }


                var snapshotSize =0L

            myRef.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    snapshotSize = snapshot.child(mUserIdReplaceDotToStar).childrenCount
                    // Log.d("snapshotSize", snapshotSize.toString())

                    if(snapshot.hasChild(mUserIdReplaceDotToStar)){  // 기존에 알람리스트등록 이력이있는 유저인경우

                        // 해당국가의 알림이 이미 설정 되어있던 경우  -> 삭제하기
                        if(isAlarmSet){
                            for(i in 0 .. snapshotSize){
                                val pushCountry = (snapshot.child(mUserIdReplaceDotToStar).child(i.toString()).child("push_country").value).toString()

                                if(pushCountry == selectedCountry){
                                    deleteAlarm(i.toInt())
                                    mAlarmList.add(MyAlarmData(pushCountry))
                                    break
                                }
                            }
                        }
                        // 해당국가의 알람이 없었던 경우  ->  추가하기
                        else{
                            // 오류!!
//                            setAlarm(snapshotSize.toInt())
                        }
                    }
                    else{
                        // 처음으로 알람리스트 등록하는 유저인경우
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

            //
//            val query: Query = myRef.child(mUserIdReplaceDotToStar).orderByChild("push_country").equalTo(selectedCountry)
//            Log.d("query",query.toString())

//            if(isAlarmSet){
//                for(i in 0 .. snapshotSize){
//                   val pushCountry = (myRef.child(mUserIdReplaceDotToStar).child(i.toString()).child("push_country")).push()
//                    Log.d("key",pushCountry.toString())
//                    if(pushCountry == selectedCountry){
//                        deleteAlarm(i.toInt())
//                        break
//                    }
//
//                    //Log.d("알림${i}",pushCountry)
//                    mAlarmList.add(MyAlarmData(pushCountry))
//                    Log.d("pushCountry", pushCountry.toString())
//                }
//            }
        }
    }
}