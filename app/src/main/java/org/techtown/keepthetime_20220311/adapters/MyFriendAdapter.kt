package org.techtown.keepthetime_20220311.adapters

import android.content.Context
import android.widget.ArrayAdapter
import org.techtown.keepthetime_20220311.datas.UserData

class MyFriendAdapter(
    val mContext: Context,
    val resId : Int,
    val mList : List<UserData>
) : ArrayAdapter<UserData>(mContext, resId, mList) {

}