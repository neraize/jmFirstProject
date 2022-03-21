package com.neraize.jmfirstproject.api

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.*


class FirebaseDbConnect {


    companion object{

        private lateinit var rootRef:DatabaseReference
        private lateinit var myRef:DatabaseReference

        fun getFirebaseDb():DatabaseReference{
            rootRef = FirebaseDatabase.getInstance().getReference()
            myRef = rootRef.getRoot();
            return  myRef
        }

    }

//    lateinit var database: FirebaseDatabase
//    lateinit var myRef: DatabaseReference

//    fun firebaseWrite(){
//        // Write a message to the database
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");
//    }

//    fun firebaseRead(){
//        // Read from the database
//        // Read from the database
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue(String::class.java)
//                Log .d(TAG, "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })
//    }

}