package org.techtown.keepthetime_20220311.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.keepthetime_20220311.R
import org.techtown.keepthetime_20220311.datas.UserData

class RequestedUserRecyclerAdapter(
    val mContext : Context,
    val mList : List<UserData>
) : RecyclerView.Adapter<RequestedUserRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        val imgProfile = view.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = view.findViewById<TextView>(R.id.txtNickname)
        val imgSocialLoginLogo = view.findViewById<ImageView>(R.id.imgSocialLoginLogo)
        val txtEmail = view.findViewById<TextView>(R.id.txtEmail)       // id 찾아오기
        val btnAccept = view.findViewById<Button>(R.id.btnAccept)
        val btnDeny = view.findViewById<Button>(R.id.btnDeny)

        fun bind(data: UserData) {      // 실제 값을 붙여주는 함수, 이너클래스 기능만 담당. 실제 사용은 바인드뷰홀더에서

            txtNickname.text = data.nick_name
            Glide.with(mContext).load(data.profile_img).into(imgProfile)

            when(data.provider) {
                "default" -> {
                    imgSocialLoginLogo.visibility = View.GONE
                    txtEmail.text = data.email
                }
                "kakao" -> {
                    imgSocialLoginLogo.visibility = View.VISIBLE
                    imgSocialLoginLogo.setImageResource(R.drawable.kakao_logo)
                    txtEmail.text = "카카오 로그인"
                }
                "facebook" -> {
                    imgSocialLoginLogo.visibility = View.VISIBLE
                    imgSocialLoginLogo.setImageResource(R.drawable.facebook_logo)
                    txtEmail.text = "페이스북 로그인"
                }
                "naver" -> {
                    imgSocialLoginLogo.visibility = View.VISIBLE
                    imgSocialLoginLogo.setImageResource(R.drawable.naver_logo)
                    txtEmail.text = "네이버 로그인"
                }
            }

//            btnAccept, btnDeny 모두 같은 API 호출, (하는 행동이 같다.)
//            => type 파라미터에 첨부하는 값만 다름. ("수락" / "거절")

//            두개의 버튼이 눌리면 할 일을 하나의 변수에 담아두자.(같은 일 하도록)
//            할 일 : Interface => 정석 : object : 인터페이스종류 {}
//            축약문법 (lambda) => 인터페이스종류 {}

            val ocl = View.OnClickListener{
//                서버에 수락 / 거절 의사 전달.
//                수락버튼 : 수락 / 거절버튼 : 거절 => 어느 버튼을 눌렀는지 파악 가능해야, 파라미터도 다르게 전달.
//                it 변수 : 클릭된 버튼을 담고있는 역할.
//                tag 속성 : 아무말이나 적어도 되는 일종의 메모. 수락 / 거절 등 보내야 할 값을 메모해두자.

                val tagStr = it.tag.toString()

                Log.d("보낼 파라미터 값", tagStr)
            }

            btnAccept.setOnClickListener(ocl)
            btnDeny.setOnClickListener(ocl)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {   // 위의 ViewHolder 객체 만들어달라.

        val view = LayoutInflater.from(mContext).inflate(R.layout.requested_user_list_item,parent,false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {        //실제 데이터 뽑아서 연동

        val data = mList[position]
        holder.bind(data)       // 홀더가 가지고 있는 바인드 함수에 데이터 넣어서 실행




    }

    override fun getItemCount() = mList.size

}

