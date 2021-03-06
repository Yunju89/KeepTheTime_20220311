package org.techtown.keepthetime_20220311.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.keepthetime_20220311.R
import org.techtown.keepthetime_20220311.api.APIList
import org.techtown.keepthetime_20220311.api.ServerAPI
import org.techtown.keepthetime_20220311.datas.UserData
import retrofit2.create

class SearchedUserRecyclerAdapter(
    val mContext: Context,
    val mList: ArrayList<UserData>,
) : RecyclerView.Adapter<SearchedUserRecyclerAdapter.MyViewHolder>() {  //MyViewHolder 적으면 자동적용

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {    //ViewHolder 적으면 자동적용  //view:View 생성자 받아와서, view 넘겨줌.

        val imgProfile = view.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = view.findViewById<TextView>(R.id.txtNickname)
        val txtEmail = view.findViewById<TextView>(R.id.txtEmail)
        val imgSocialLoginLogo = view.findViewById<ImageView>(R.id.imgSocialLoginLogo)
        val btnAddFriend = view.findViewById<Button>(R.id.btnAddFriend)

        //        실 데이터 반영 기능이 있는 함수
        fun bind(data: UserData) {
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

//            친구 추가 버튼이 눌리면 할 일 => 친구 추가 요청 API 호출
//            어댑터에서 => API 호출 => 레트로핏 객체 직접 생성해서 호출

            val retrofit = ServerAPI.getRetrofit(mContext)      // 레트로핏 객체를 서버api 통해 받아오기
            val apiList = retrofit.create(APIList::class.java)      //apiList 객체를 레트로핏 이용해 생성.(APIList 인터페이스)






        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

//        xml inflate 해와서 => 이를 가지고, MyViewHolder 객체로 생성. 리턴
//        재사용성을 알아서 보존해줌.
//            LayoutInflater = xml 객체로 만들어주기.(코드에 쓸 수 있게)
        val row = LayoutInflater.from(mContext)
            .inflate(R.layout.searched_user_list_item, parent, false)//view 넘겨주기위해 inflate
        return MyViewHolder(row)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        실제 데이터 반영 함수
        val data = mList[position]

//        이 함수에서 직접 코딩하면 => holder.UI 변수로 매번 holder 단어 써야함.
//        holder 변수의 멤버변수들을 사용할 수 있는것 처럼, 함수도 사용할 수 있다.

        holder.bind(data)

    }

    //    몇개의 아이템을 보여줄 예정인지? => 데이터 목록의 갯수 만큼.
    override fun getItemCount() = mList.size
}

