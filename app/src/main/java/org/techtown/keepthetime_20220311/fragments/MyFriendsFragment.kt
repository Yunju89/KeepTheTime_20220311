package org.techtown.keepthetime_20220311.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.keepthetime_20220311.R
import org.techtown.keepthetime_20220311.adapters.MyFriendRecyclerAdapter
import org.techtown.keepthetime_20220311.databinding.FragmentMyFriendsBinding
import org.techtown.keepthetime_20220311.datas.BasicResponse
import org.techtown.keepthetime_20220311.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendsFragment : BaseFragment() {

    lateinit var binding : FragmentMyFriendsBinding

    val mMyFriendList = ArrayList<UserData>()

    lateinit var mFriendAdapter : MyFriendRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_friends, container, false)
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

        mFriendAdapter = MyFriendRecyclerAdapter(mContext, mMyFriendList )
        binding.myFriendRecyclerView.adapter = mFriendAdapter
        binding.myFriendRecyclerView.layoutManager = LinearLayoutManager(mContext)


        getMyFriendsFromServer()
    }

    fun getMyFriendsFromServer(){

        apiList.getRequestMyFriendList("my").enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if(response.isSuccessful){
                    val br = response.body()!!

                    mMyFriendList.clear()

                    mMyFriendList.addAll(br.data.friends)

                    mFriendAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}