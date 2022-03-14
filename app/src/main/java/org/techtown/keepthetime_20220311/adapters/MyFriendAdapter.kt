package org.techtown.keepthetime_20220311.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.techtown.keepthetime_20220311.R
import org.techtown.keepthetime_20220311.datas.UserData

class MyFriendAdapter(
    val mContext: Context,
    val resId : Int,
    val mList : List<UserData>
) : ArrayAdapter<UserData>(mContext, resId, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tmpRow = convertView
        if(tmpRow == null){
            tmpRow = LayoutInflater.from(mContext).inflate(R.layout.my_friend_list_item, null)
        }
        val row = tmpRow!!

        val data = mList[position]

        val txtEmail = row.findViewById<TextView>(R.id.txtEmail)
        val txtNickname = row.findViewById<TextView>(R.id.txtNickname)
        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)

        Glide.with(mContext).load(data.profile_img).into(imgProfile)
        txtEmail.text = data.email
        txtNickname.text = data.nick_name





        return row
    }
}