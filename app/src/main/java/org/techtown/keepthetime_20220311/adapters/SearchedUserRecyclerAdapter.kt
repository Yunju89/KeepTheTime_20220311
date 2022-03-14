package org.techtown.keepthetime_20220311.adapters

import android.content.Context
import android.service.autofill.UserData
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchedUserRecyclerAdapter(
    val mContext: Context,
    val mList : List<UserData>,
) : RecyclerView.Adapter<SearchedUserRecyclerAdapter.MyViewHolder>() {  //MyViewHolder 적으면 자동적용

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

//    몇개의 아이템을 보여줄 예정인지? => 데이터 목록의 갯수 만큼.
    override fun getItemCount() = mList.size
    }

}