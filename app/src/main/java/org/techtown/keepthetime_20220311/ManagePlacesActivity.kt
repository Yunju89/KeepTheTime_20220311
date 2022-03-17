package org.techtown.keepthetime_20220311

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.keepthetime_20220311.adapters.MyPlaceRecyclerAdapter
import org.techtown.keepthetime_20220311.databinding.ActivityManagePlacesBinding
import org.techtown.keepthetime_20220311.datas.BasicResponse
import org.techtown.keepthetime_20220311.datas.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManagePlacesActivity : BaseActivity() {

    lateinit var binding : ActivityManagePlacesBinding

    val mPlaceList = ArrayList<PlaceData>()

    lateinit var mPlaceAdapter : MyPlaceRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_manage_places)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        btnAdd.setOnClickListener {

//            장소 추가 화면 이동


        }

    }

    override fun setValues() {

        txtTitle.text = "내 출발 장소 관리"
        btnAdd.visibility = View.VISIBLE    // 숨겨져 있던 추가 버튼을 보이게

        mPlaceAdapter = MyPlaceRecyclerAdapter(mContext,mPlaceList)
        binding.myPlacesRecyclerView.adapter = mPlaceAdapter
        binding.myPlacesRecyclerView.layoutManager = LinearLayoutManager(mContext)


    }

    override fun onResume() {       // 액티비티가 화면에 등장할 때 실행
        super.onResume()
        getMyPlacesFromServer()
    }

    fun getMyPlacesFromServer(){

        apiList.getRequestMyPlaceList().enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful){

                    val br = response.body()!!

                    mPlaceList.clear()
                    mPlaceList.addAll(br.data.places)

                    mPlaceAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })


    }

}