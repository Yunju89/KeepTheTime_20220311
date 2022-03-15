package org.techtown.keepthetime_20220311

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.techtown.keepthetime_20220311.databinding.ActivityEditAppointmentBinding
import java.text.SimpleDateFormat
import java.util.*

class EditAppointmentActivity : BaseActivity() {
    lateinit var binding : ActivityEditAppointmentBinding

//    약속 시간 일 / 시 저장해줄 Calendar (월 값이 0~11로 움직이게 맞춰져 있다.)

    val mSelectedAppointmentDateTime = Calendar.getInstance()   // 기본 값 : 현재 일시

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_appointment)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

//        날짜 선택 텍스트뷰 클릭 이벤트 - DatePickerDialog
        binding.txtDate.setOnClickListener {
            val dsl = object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//                    연/월/일은  Java / Kotlin  언어 기준으로(0~11월) 월 값을 줌. (사람은 1~12월)
//                    주는 그대로 Calendar 에 set 하게 되면, 올바른 월로 세팅됨.

                    mSelectedAppointmentDateTime.set(year, month, dayOfMonth)   // 연월일 한번에 세팅

//                    약속일자의 문구를 22/03/08 등의 형식으로 바꿔서 보여주자.
//                    SimpleDateFormat 활용 => 월 값도 알아서 보정.

                    val sdf = SimpleDateFormat("yy/MM/dd")

                    binding.txtDate.text = sdf.format(mSelectedAppointmentDateTime.time)

                }
            }

            val dpd = DatePickerDialog(
                mContext,   // 어느화면
                dsl,        // 날짜 선택시 할 일
                mSelectedAppointmentDateTime.get(Calendar.YEAR),        //팝업 출현 시 기본 선택 값
                mSelectedAppointmentDateTime.get(Calendar.MONTH),
                mSelectedAppointmentDateTime.get(Calendar.DAY_OF_MONTH)
            ).show()

        }

//        시간 선택 텍스트뷰 클릭 이벤트 - TimePickDialog

        binding.txtTime.setOnClickListener {

            val tsl = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                    Toast.makeText(mContext, "${hourOfDay}시${minute}분 선택", Toast.LENGTH_SHORT).show()

                }

            }

            val tpd = TimePickerDialog(
                mContext,
                tsl,
                18,
                0,
                false
            ).show()

        }

    }

    override fun setValues() {

    }
}