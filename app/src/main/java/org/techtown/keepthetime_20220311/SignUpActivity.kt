package org.techtown.keepthetime_20220311

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.techtown.keepthetime_20220311.databinding.ActivitySignInBinding
import org.techtown.keepthetime_20220311.databinding.ActivitySignUpBinding
import org.techtown.keepthetime_20220311.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSingUp.setOnClickListener {
            val inputId = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

//            회원가입 API 호출 ( PUT - "/user")
            apiList.putRequestSignUp(inputId, inputPw, inputNickname).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){

                        val br = response.body()!!

                        Toast.makeText(mContext, "${br.data.user.id}번째 회원", Toast.LENGTH_SHORT).show()

                        finish()
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })


        }

    }

    override fun setValues() {

    }
}