package org.techtown.keepthetime_20220311

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import org.techtown.keepthetime_20220311.databinding.ActivityViewMapBinding
import org.techtown.keepthetime_20220311.datas.AppointmentData

class ViewMapActivity : BaseActivity() {

    lateinit var binding : ActivityViewMapBinding

    lateinit var mAppointment : AppointmentData

    lateinit var mapView : MapView

    var marker : Marker? = null   // 지도에 표시될 하나의 마커. 처음에는 찍지 않은 상태
    var path : PathOverlay? = null  // 출발지 ~ 도착지 까지 보여줄 경로 선. 처음에는 보이지 않는 상태.
    var mSelectedLatLng : LatLng? = null    // 약속 장소의 위/경도도 처음에는 설정하지 않은 상태.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_map)

        mAppointment = intent.getSerializableExtra("appointment") as AppointmentData

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {


        binding.mapView.getMapAsync {

            val naverMap = it




        }

    }
}