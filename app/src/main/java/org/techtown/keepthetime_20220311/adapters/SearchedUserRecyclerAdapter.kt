package org.techtown.keepthetime_20220311.adapters

import android.content.Context
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.keepthetime_20220311.R

class SearchedUserRecyclerAdapter(
    val mContext: Context,
    val mList: ArrayList<org.techtown.keepthetime_20220311.datas.UserData>,
) : RecyclerView.Adapter<SearchedUserRecyclerAdapter.MyViewHolder>() {  //MyViewHolder 적으면 자동적용

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){    //ViewHolder 적으면 자동적용  //view:View 생성자 받아와서, view 넘겨줌.

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

//        xml inflate 해와서 => 이를 가지고, MyViewHolder 객체로 생성. 리턴
//        재사용성을 알아서 보존해줌.
//            LayoutInflater = xml 객체로 만들어주기.(코드에 쓸 수 있게)
        val row = LayoutInflater.from(mContext).inflate(R.layout.searched_user_list_item, parent, false)//view 넘겨주기위해 inflate
        return MyViewHolder( row )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        실제 데이터 반영 함수

    }

//    몇개의 아이템을 보여줄 예정인지? => 데이터 목록의 갯수 만큼.
    override fun getItemCount() = mList.size
    }

