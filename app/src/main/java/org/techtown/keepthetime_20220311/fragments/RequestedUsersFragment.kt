package org.techtown.keepthetime_20220311.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.keepthetime_20220311.R
import org.techtown.keepthetime_20220311.adapters.RequestedUserRecyclerAdapter
import org.techtown.keepthetime_20220311.databinding.FragmentMyFriendsBinding
import org.techtown.keepthetime_20220311.databinding.FragmentRequestedUsersBinding
import org.techtown.keepthetime_20220311.datas.BasicResponse
import org.techtown.keepthetime_20220311.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestedUsersFragment : BaseFragment() {

    lateinit var binding : FragmentRequestedUsersBinding

    val mRequestedUserList = ArrayList<UserData>()        // 어댑터 제작 후 데이터 담아둘 리스트

    lateinit var mRequestUserAdapter : RequestedUserRecyclerAdapter       // 어댑터 생성 및 연결

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_requested_users, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupEvents()
        setValues()
    }





    override fun setupEvents() {

    }

    override fun setValues() {

        getRequestedUsersFromServer()


        mRequestUserAdapter = RequestedUserRecyclerAdapter(mContext, mRequestedUserList)
        binding.requestUserRecyclerView.adapter = mRequestUserAdapter       // 리싸이클러뷰의 어댑터로 엠어댑터 넣어줌

        binding.requestUserRecyclerView.layoutManager = LinearLayoutManager(mContext)

//        나에게 친구 요청한 사람 목록을 > 리싸이클러뷰로 보여주기.
//        API : getRequestFriendList 함수 -> "requested:로 대입

//        apiList.getRequestMyFriendList()

    }

    fun getRequestedUsersFromServer(){       // 어댑터생성, 담을 리스트 생성 후 API 호출

        apiList.getRequestFriendList("requested").enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){

                    mRequestedUserList.clear()
                    mRequestedUserList.addAll(response.body()!!.data.friends)

                    mRequestUserAdapter.notifyDataSetChanged()    //어댑터 새로고침

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }


}