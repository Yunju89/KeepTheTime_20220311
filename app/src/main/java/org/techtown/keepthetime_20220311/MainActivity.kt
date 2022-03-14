package org.techtown.keepthetime_20220311

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.techtown.keepthetime_20220311.databinding.ActivityMainBinding
import org.techtown.keepthetime_20220311.datas.BasicResponse
import org.techtown.keepthetime_20220311.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        GET - /user 접근해서, 내 정보 조회
//        토큰 값 필요함 => 로그인 성공 시 토큰 저장, ContextUtil 에서 추출해서 사용.

         apiList.getRequestMyInfo(ContextUtil.getLoginUserToken(mContext)).enqueue(object : Callback<BasicResponse>{
             override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                 if(response.isSuccessful){

//                     JSON 응답을 서버에서 보고, 파싱 진행 => 파싱 된 변수들을 활용.
//                     이미 파싱해 둔 구조 재활용.
                     val br = response.body()!!     // 타이핑 덜 하기 위해 옮겨담는 변수

                     binding.txtUserNickName.text = br.data.user.nick_name

                 }

             }

             override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

             }

         })


    }


}