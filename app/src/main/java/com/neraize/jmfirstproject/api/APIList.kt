package com.neraize.jmfirstproject.api

import com.neraize.jmfirstproject.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface APIList {

    // BASE_URL 에 해당하는 서버에서, 어떤 기능들을 사용할건지 명시

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") id:String,
        @Field("password") password:String,
    ) : Call<BasicResponse>

//    {
//        "code": 200,
//        "message": "로그인 성공.",
//        "data": {
//        "user": {
//        "id": 115,
//        "provider": "default",
//        "uid": null,
//        "email": "test11@naver.com",
//        "ready_minute": 30,
//        "nick_name": "test11",
//        "app_maker": null,
//        "profile_img": "https://s3.ap-northeast-2.amazonaws.com/neppplus.finalproject.202109/profile_imgs/default_profile_icon.jpg",
//        "created_at": "2022-03-11 08:13:26",
//        "updated_at": "2022-03-11 08:13:26"
//    },
//        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6MTE1LCJlbWFpbCI6InRlc3QxMUBuYXZlci5jb20iLCJwYXNzd29yZCI6IjBjYzkwNTA1YmJmYTU0ZDQzZTNkMWQ5MmJkOGU2NjVmIn0.n9YThkarTKL5rzt2B7_j6FQwkzQCMGZa6I1AQK-XNRz4rZCcz62WxOQZQOGn2zzw79H2CdZXq8bp-NwhX9tdTA"
//    }
//    }

    @FormUrlEncoded
    @PUT("/user")
    fun postRequestSignUp(
        @Field("email") email:String,
        @Field("password") password: String,
        @Field("nick_name") nickname:String,
    ):Call<BasicResponse>


    @GET("/user")
    fun getRequestMyInfo(
        @Header("X-Http-Token") token:String,
    ) :Call<BasicResponse>


    @GET("/user/check")
    fun getRequestDuplicateCheck(
        @Query("type") type:String,
        @Query("value") value:String,
    ) : Call<BasicResponse>
}