package org.techtown.keepthetime_20220311


import android.os.Bundle
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.keepthetime_20220311.adapters.SearchedUserRecyclerAdapter
import org.techtown.keepthetime_20220311.databinding.ActivitySearchUserBinding
import org.techtown.keepthetime_20220311.datas.BasicResponse
import org.techtown.keepthetime_20220311.datas.UserData
import org.techtown.keepthetime_20220311.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserActivity : BaseActivity() {

    lateinit var binding : ActivitySearchUserBinding

    val mSearchUserList = ArrayList<UserData>()

    lateinit var mAdapter : SearchedUserRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search_user)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSearch.setOnClickListener {
            val inputKeyword = binding.edtNickname.text.toString()

            apiList.getRequestSearchUser(
                ContextUtil.getLoginUserToken(mContext),
                inputKeyword
            ).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
//                    기존의 검색 목록은 삭제해야, 누적으로 추가되는 것을 막을 수 있다.

                    mSearchUserList.clear()

                    if(response.isSuccessful){
                        val br = response.body()!!

                        mSearchUserList.addAll(br.data.users)
                        mAdapter.notifyDataSetChanged()

                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })
        }


    }

    override fun setValues() {

        mAdapter = SearchedUserRecyclerAdapter(mContext, mSearchUserList)
        binding.userListRecyclerView.adapter = mAdapter

//        리싸이클러뷰는 어떤 모양으로 목록을 표현할지도 설정해야 화면에 데이터가 나옴.
        binding.userListRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }
}