package org.techtown.keepthetime_20220311.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.keepthetime_20220311.R
import org.techtown.keepthetime_20220311.datas.AppointmentData

class ManagePlaceRecyclerAdapter(
    val mContext : Context,
    val mList : List<AppointmentData>
): RecyclerView.Adapter<ManagePlaceRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.activity_manage_places,parent,false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



    }

    override fun getItemCount() = mList.size
}