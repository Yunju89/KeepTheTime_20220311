package org.techtown.keepthetime_20220311.api

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIList {

//    BASE_URL 에 해당하는 서버에서, 어떤 기능들을 사용할건지 명시.

    @FormUrlEncoded     // 파라미터 중에, Field (formData)에 담아야 하는 파라미터가 있다면 필요한 구문.
    @POST("/user")
    fun postRequestLogin(             // @ Field 안에 키값을 담아줌
        @Field("email") email : String ,
        @Field("password") pw : String )  : Call<JSONObject>


}