package org.techtown.keepthetime_20220311

import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import org.techtown.keepthetime_20220311.databinding.ActivityViewMapBinding
import org.techtown.keepthetime_20220311.datas.AppointmentData

class ViewMapActivity : BaseActivity() {

    lateinit var binding : ActivityViewMapBinding

    lateinit var mAppointment : AppointmentData

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

        binding.naverMapView.getMapAsync {

            val naverMap = it

//            도착지 자체를 변수화
            val destLatLng = LatLng(mAppointment.latitude, mAppointment.longitude)

//            도착지로 카메라 이동
            val cameraUpdate = CameraUpdate.scrollTo( destLatLng )
            naverMap.moveCamera(cameraUpdate)

//            마커 생성
            val marker = Marker()
            marker.position = destLatLng
            marker.map = naverMap





        }

    }
}