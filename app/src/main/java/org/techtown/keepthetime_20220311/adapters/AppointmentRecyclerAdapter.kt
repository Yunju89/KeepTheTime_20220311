package org.techtown.keepthetime_20220311.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.keepthetime_20220311.datas.AppointmentData

class AppointmentRecyclerAdapter(
    val mContext : Context,
    val mList : List<AppointmentData>
) : RecyclerView.Adapter<AppointmentRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount
}