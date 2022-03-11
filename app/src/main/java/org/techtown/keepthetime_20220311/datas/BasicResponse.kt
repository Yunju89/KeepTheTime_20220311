package org.techtown.keepthetime_20220311.datas


//  서버가 주는 응답의 제일 기본 형태인 code, message, data 파싱해주는 클래스
//  레트로핏과 연계하면, 파싱이 자동 진행됨.

class BasicResponse(
    val code : Int,
    val message : String,
    val data : DataResponse,
) {

}