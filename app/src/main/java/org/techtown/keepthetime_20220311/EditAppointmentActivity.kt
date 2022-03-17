package org.techtown.keepthetime_20220311

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import org.techtown.keepthetime_20220311.adapters.StartPlaceSpinnerAdapter
import org.techtown.keepthetime_20220311.databinding.ActivityEditAppointmentBinding
import org.techtown.keepthetime_20220311.datas.BasicResponse
import org.techtown.keepthetime_20220311.datas.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditAppointmentActivity : BaseActivity() {
    lateinit var binding : ActivityEditAppointmentBinding

//    약속 시간 일 / 시 저장해줄 Calendar (월 값이 0~11로 움직이게 맞춰져 있다.)

    val mSelectedAppointmentDateTime = Calendar.getInstance()   // 기본 값 : 현재 일시

//    약속 장소 관련 멤버변수.
    var marker : Marker? = null   // 지도에 표시될 하나의 마커. 처음에는 찍지 않은 상태
    var path : PathOverlay? = null  // 출발지 ~ 도착지 까지 보여줄 경로 선. 처음에는 보이지 않는 상태.

    var mSelectedLatLng : LatLng? = null    // 약속 장소의 위/경도도 처음에는 설정하지 않은 상태.

    var naverMap : NaverMap? = null

//    내 출발 장소 목록
    val mStartPlaceList = ArrayList<PlaceData>()

//    선택한 출발 장소
    var mSelectedStartPlace : PlaceData? = null

    lateinit var mStartPlaceAdapter : StartPlaceSpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_appointment)

        setupEvents()
        setValues()

    }


    override fun setupEvents() {
        
//        도전과제 : 스피너의 이벤트 처리
        
        binding.startPlaceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
//                몇번째 아이템이 선택 되었는지? position 이알려줌.
                mSelectedStartPlace = mStartPlaceList[position]

//             네이버 지도보다 로딩이 느릴 수 있다.
//              출발 장소도 로딩이 끝나면, 다시 지도 세팅 진행
                setNaverMap()

//                선택한 출발지 ~ 지도에서 클릭한 도착지 까지의 이동 경로 / 교통 정보를 표현
                findWay()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                
            }

        }

//        스크롤 보조용 텍스트뷰에 손이 닿으면 => 스크롤뷰의 이벤트 일시정지. (지도만 움직이게)
        binding.txtScrollHelp.setOnTouchListener { v, event ->

//            스크롤뷰의 이벤트 정지
            binding.scrollView.requestDisallowInterceptTouchEvent(true)

//            텍스트뷰의 터치 이벤트만? false. => 뒤에 가려져있는 지도도 터치를 허용해줘야 함.
            return@setOnTouchListener false
        }


//        저장 버튼이 눌리면
        binding.btnSave.setOnClickListener {

//            입력 값들이 제대로 되어있는지? 확인 => 잘못돠었다면 막아주자.(input validation)

            val inputTitle = binding.edtTitle.text.toString()
//            입력하지 않았다면 거부(예시)
            if(inputTitle.isEmpty()){
                Toast.makeText(mContext, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            시간을 선택하지 않았다면 막자.
//            기준? txtDate, txtTime 두개의 문구 중 하나라도 처음 문구 그대로면 입력을 안했다고 간주

            if(binding.txtDate.text == "약속일자"){
                Toast.makeText(mContext, "일자를 선택 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.txtTime.text == "약속 시간"){
                Toast.makeText(mContext, "시간을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            선택한 일시가, 지금보다 이전의 일시라면 "현재 이후의 시간으로 선택해주세요" 토스트
            val now = Calendar.getInstance()    // 저장 버튼을 누른 현재 시간.

            if(mSelectedAppointmentDateTime.timeInMillis < now.timeInMillis){
                Toast.makeText(mContext, " 현재 이후의 시간으로 선택해주세요. ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            장소 이름 등록
            val inputPlaceName = binding.edtPlaceName.text.toString()
            if (inputPlaceName.isEmpty()){
                Toast.makeText(mContext, "장소 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            장소를 선택했는지? 안했다면 등록 거부.
            if(mSelectedLatLng == null) {
                Toast.makeText(mContext, "약속 장소를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("선택한약속장소 - 위도", "위도${mSelectedLatLng!!.latitude}")
            Log.d("선택한약속장소 - 경도", "경도${mSelectedLatLng!!.longitude}")

            val selectedStartPlace = mStartPlaceList[binding.startPlaceSpinner.selectedItemPosition]   // 지금 선택되어 있는 아이템이 몇번째 아이템인지, 위치얻어낼 수 있다.




//            약속일시 - yyyy-MM-dd HH:mm 양식을 서버가 지정해서 요청.
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")

            apiList.postRequestAddAppointment(
                inputTitle,
                sdf.format(mSelectedAppointmentDateTime.time),
                selectedStartPlace.name,
                selectedStartPlace.latitude,
                selectedStartPlace.longitude,
                inputPlaceName,
                mSelectedLatLng!!.latitude,
                mSelectedLatLng!!.longitude,
            ).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(mContext, "약속을 등록했습니다", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

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

//                    약속 일시의 시간으로 설정.
                    mSelectedAppointmentDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    mSelectedAppointmentDateTime.set(Calendar.MINUTE, minute)

                    val sdf = SimpleDateFormat("a h시 m분")
                    binding.txtTime.text = sdf.format(mSelectedAppointmentDateTime.time)

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

//        네이버 지도 객체 얻어오기 => 얻어와지면 할 일 코딩 ( Interface ) 코딩
        binding.naverMapView.getMapAsync {

//            지도 로딩이 끝나고 난 후에 얻어낸 온전한 지도 객체
            naverMap = it

            setNaverMap()


        }

//       내 출발장소 목록 불러오기
        getMyStartPlaceListFromServer()

//        스피너 어댑터 연결 -> 리스트뷰와 동일
        mStartPlaceAdapter = StartPlaceSpinnerAdapter(mContext, R.layout.start_place_spinner_list_item, mStartPlaceList)
        binding.startPlaceSpinner.adapter = mStartPlaceAdapter


    }

    fun setNaverMap(){

//        로딩이 끝난 네이버지도를 가지고 실행할 코드

//        출발지점이 선택 되어야 세팅 진행.
        if(mSelectedStartPlace == null) {
            return  // 우선 함수 강제 종료
        }

//        네이버 맵도 불러와져야 세팅 진행.

        if(naverMap == null){
            return  // 이상황도 함수 강제 종료료
        }


//           지도 시작지점 : 선택된 출발 지점
        val coord = LatLng(mSelectedStartPlace!!.latitude, mSelectedStartPlace!!.longitude)

//            coord 에 설정한 좌표로 > 네이버 지도의 카메라 이동
        val cameraUpdate = CameraUpdate.scrollTo(coord)
        naverMap!!.moveCamera(cameraUpdate)

//            첫 마커 좌표 -> 학원 위치 -> null

//            val marker = Marker()   => 멤버변수로 하나의 마커만 만들어서 관리하자.
//            marker = Marker()
//            marker!!.position = coord
//            marker!!.map = naverMap

//            처음 선택된 좌표 -> 학원 위치 -> null
        mSelectedLatLng = coord

//            지도 클릭 이벤트
        naverMap!!.setOnMapClickListener { pointF, latLng ->

            Log.d("클릭된 위/경도", "위도 : ${latLng.latitude}, 경도 : ${latLng.longitude}")

//                (찍혀있는 마커가 없다면, )마커를 새로 추가
//                val newMarker = Marker()
//                newMarker!!.position = latLng
//                newMarker!!.map = naverMap

//                그 마커의 위치 / 지도 적용
            if(marker==null){
                marker = Marker()
            }

            marker!!.position = latLng
            marker!!.map = naverMap

//                약속 장소도 새 좌표로 설정.
            mSelectedLatLng = latLng


//                출발 coord ~ 선택 latLng 까지 대중교통 경로를 그려보자. (pathOverlay 기능 활용), + ODSay 라이브러리 활용


            if(path == null){
                path = PathOverlay()
            }

//                ArrayList 만들어서 출발지, 도착지 추가
            val coordList = ArrayList<LatLng>()

            coordList.add( coord )  // 출발지 임시 학원으로
            coordList.add ( latLng )

            path!!.coords = coordList

            path!!.map = naverMap

//            길찾기 API 실행
            findWay()

        }


    }

//    길찾기 관련 코드를 별도 함수로. => 여러곳에서 활용 가능
    fun findWay(){

//    출발지 / 도착지 모두 불러와져야 길찾기 진행

    if(mSelectedStartPlace == null || mSelectedLatLng == null){
        return  // 좌표가 하나라도 없으면 강제 종료
    }

        val myODSayService = ODsayService.init(mContext, "9Xh+Oz2ktqZJHEOLmRJL5TekT/lucTE3zXGFzwZ5otA")

        myODSayService.requestSearchPubTransPath(
            mSelectedStartPlace!!.longitude.toString(),
            mSelectedStartPlace!!.latitude.toString(),
            mSelectedLatLng!!.longitude.toString(),
            mSelectedLatLng!!.latitude.toString(),
            null,
            null,
            null,
            object : OnResultCallbackListener{
                override fun onSuccess(p0: ODsayData?, p1: API?) {

                    val jsonObj = p0!!.json!!
                    Log.d("길찾기응답", jsonObj.toString())

                    val resultObj = jsonObj.getJSONObject("result")
                    Log.d("result", jsonObj.toString())

                    val pathArr =
                        resultObj.getJSONArray("path")    // 여러 추천 경로 중 첫번째만 사용해보자.

                    val firstPathObj = pathArr.getJSONObject(0)     // 0번째 경로 추출
                    Log.d("첫번째 경로", firstPathObj.toString())

//                              첫번째 경로를 지나는 모든 정거장의 위경도 값을 담을 목록
                    val stationLatLngList = ArrayList<LatLng>()

//                            출발지 좌표를 정거장 목록에 먼저 추가.
                    stationLatLngList.add(LatLng(mSelectedStartPlace!!.latitude, mSelectedStartPlace!!.longitude))

//                            불광 ~ 강남 : 도보5분 / 지하철30분 / 버스30분 / 도보5분
                    val subPathArr = firstPathObj.getJSONArray("subPath")

                    for (i in 0 until subPathArr.length()) {
                        val subPathObj = subPathArr.getJSONObject(i)

//                                둘러보려는 경로가, 정거장 목록을 내려준다면(지하철, 버스) => 내부 파싱
                        if (!subPathObj.isNull("passStopList")) {

                            val passStopListObj = subPathObj.getJSONObject("passStopList")
                            val stationArr = passStopListObj.getJSONArray("stations")


//                                    실제 정거장 목록 파싱 => 각 정거장 위도/경도 추출 가능 => ArrayList 담아서 경로선의 좌표로 활용
                            for (j in 0 until stationArr.length()) {

                                val stationObj = stationArr.getJSONObject(j)

//                                        위도 (y좌표), 경도 (x좌표)
                                val lat = stationObj.getString("y").toDouble()
                                val lng = stationObj.getString("x").toDouble()

//                                        네이버 지도의 좌표로 만들어서, -> ArrayList에 담자.
                                stationLatLngList.add(LatLng(lat, lng))


                            }

                        }
                    }

//                            최종 정거장~도착지까지 직선
                    stationLatLngList.add(mSelectedLatLng!!)

//                            완성된 정거장 경로들을 => path 경로로 재 설정. 지도에 새로 반영.

                    path!!.coords = stationLatLngList
                    path!!.map = naverMap


//                            (첫번째 추천 경로의) 정보 항목도 파싱.
//                            예상 소요시간 파싱 => 임시로 토스트 출력

                    val infoObj = firstPathObj.getJSONObject("info")
                    val totalTime = infoObj.getInt("totalTime") // 소요 분
                    val payment = infoObj.getInt("payment") // 소요 비용

//                            네이버 지도 라이브러리의 InfoWindow 기능 활용
                    val infoWindow = InfoWindow()
                    infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext){
                        override fun getText(p0: InfoWindow): CharSequence {
                            return "이동시간 : ${totalTime}, 비용 : ${payment} 원"
                        }
                    }
                    infoWindow.open(marker!!)

                    marker!!.setOnClickListener {

                        if (marker!!.infoWindow == null) {
                            infoWindow.open(marker!!)
                        }
                        else {
                            infoWindow.close()
                        }
                        return@setOnClickListener true
                    }

//                            연습문제 카메라를 latLng(클릭한 위치)가 가운데로 오도록 세팅
//                            공식 문서 활용 연습문제

                    val cameraUpdate = CameraUpdate.scrollTo(mSelectedLatLng!!)
                    naverMap!!.moveCamera(cameraUpdate)



                }


                override fun onError(p0: Int, p1: String?, p2: API?) {

                }
            }
        )

    }


    fun getMyStartPlaceListFromServer(){

        apiList.getRequestMyPlaceList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!

                    mStartPlaceList.clear()
                    mStartPlaceList.addAll(br.data.places)

                    mStartPlaceAdapter.notifyDataSetChanged()

                }



            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }

}