package org.techtown.keepthetime_20220311.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.techtown.keepthetime_20220311.R
import org.techtown.keepthetime_20220311.datas.UserData

class MyFriendAdapter(
    val mContext: Context,
    val resId : Int,
    val mList : List<UserData>
) : ArrayAdapter<UserData>(mContext, resId, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tmpRow = convertView
        if(tmpRow == null){
            tmpRow = LayoutInflater.from(mContext).inflate(R.layout.my_friend_list_item, null)
        }
        val row = tmpRow!!

        val data = mList[position]

        val txtEmail = row.findViewById<TextView>(R.id.txtEmail)
        val txtNickname = row.findViewById<TextView>(R.id.txtNickname)
        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
        val imgSocialLoginLogo = row.findViewById<ImageView>(R.id.imgSocialLoginLogo)

        Glide.with(mContext).load(data.profile_img).into(imgProfile)
        txtEmail.text = data.email
        txtNickname.text = data.nick_name

//        사용자의 provider - "default" : 이메일 표시, "kakao" : 카카오 로그인, "facebook" : 페북, "naver" : 네이버 로그인

        when (data.provider){
            "default" -> {
//                이메일 표시
                txtEmail.text = data.email
//                로고 이미지 숨김
                imgSocialLoginLogo.visibility = View.GONE
            }
            "kakao" -> {
//                카카오 로그인
                txtEmail.text = "카카오 로그인"
//                로고 이미지 : 카카오 아아디
                imgSocialLoginLogo.visibility = View.VISIBLE
                Glide.with(mContext).load(R.drawable.kakao_logo).into(imgSocialLoginLogo)
            }
            "facebook" -> {
                txtEmail.text = "페북 로그인"
                imgSocialLoginLogo.visibility = View.VISIBLE
                Glide.with(mContext).load(R.drawable.facebook_logo).into(imgSocialLoginLogo)
            }
            "naver" -> {
                txtEmail.text = "네이버 로그인"
                imgSocialLoginLogo.visibility = View.VISIBLE
                Glide.with(mContext).load(R.drawable.naver_logo).into(imgSocialLoginLogo)
//                Glide 는 웹의 이미지 뿐 아니라, 우리 프로젝트 내부의 이미지도 붙여넣을 수 있다.
            }
            else -> {

            }

        }




       return row
    }
}