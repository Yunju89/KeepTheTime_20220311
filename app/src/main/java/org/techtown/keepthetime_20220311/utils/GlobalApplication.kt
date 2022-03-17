package org.techtown.keepthetime_20220311.utils

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,"2c187b225f25e43751983a2140102803")

    }
}