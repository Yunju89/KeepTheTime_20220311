package org.techtown.keepthetime_20220311

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.techtown.keepthetime_20220311.adapters.MainViewPager2Adapter
import org.techtown.keepthetime_20220311.adapters.MainViewPagerAdapter
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

        binding.mainViewPager2.adapter = MainViewPager2Adapter(this)    // 변수 : Activity => 객체 : Context 대입 불가,

    }


}