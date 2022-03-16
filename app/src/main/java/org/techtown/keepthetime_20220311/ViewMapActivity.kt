package org.techtown.keepthetime_20220311

import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
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

//            Path  객체의 좌표를 설정, => naverMap 추가
            val path = PathOverlay()

//            path.coords => 출발지 / 도착지만 넣으면 일직선 연결

//            출발지~도착지 사이의 정거장이 있다면, 정거장들을 좌표로 추가

            val myODsayService = ODsayService.init(mContext, "9Xh+Oz2ktqZJHEOLmRJL5TekT/lucTE3zXGFzwZ5otA")

            myODsayService.requestSearchPubTransPath(
                mAppointment.start_longitude.toString(),
                mAppointment.start_latitude.toString(),
                mAppointment.longitude.toString(),
                mAppointment.latitude.toString(),
                null,
                null,
                null,
                object : OnResultCallbackListener{
                    override fun onSuccess(p0: ODsayData?, p1: API?) {

//                        JSONObject > jsonObj받아서 내부 파싱
                        val jsonObj = p0!!.json

                        Log.d("대중교통길찾기", jsonObj.toString())

                        val resultObj = jsonObj.getJSONObject("result")
                        Log.d("result", resultObj.toString())


                    }

                    override fun onError(p0: Int, p1: String?, p2: API?) {

                    }

                }

            )





        }

    }
}