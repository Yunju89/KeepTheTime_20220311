package org.techtown.keepthetime_20220311

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.techtown.keepthetime_20220311.databinding.ActivityMainBinding

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
//        토큰 값 필요함 => 로그인 성공 시 토큰 저장, ContextUtil에서 추출해서 사용.

    }


}