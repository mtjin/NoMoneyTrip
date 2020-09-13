package com.mtjin.nomoneytrip.views.main

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.databinding.ActivityMainBinding
import com.mtjin.nomoneytrip.utils.PRODUCT
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
        getHashKey()
        //insertProductTestData()
    }

    private fun insertProductTestData() {
        for (i in 1..5) {
            Firebase.database.reference.child(PRODUCT).child(i.toString())
                .setValue(
                    Product(
                        address = "충청남도 청양군 대치면 까치내로 1063-1",
                        animal = true,
                        checkIn = "오전 9시",
                        checkOut = "다음날 오전 11시",
                        city = "서울",
                        content = "청양 칠갑산 산꽃마을",
                        hashTagList = listOf("#농촌 봉사", "#농촌 체험$i"),
                        homePage = "https://www.naver.com/",
                        id = i.toString(),
                        imageList = listOf(
                            "https://firebasestorage.googleapis.com/v0/b/nomoneytrip-63056.appspot.com/o/1.PNG?alt=media&token=7dc1ae5d-d32a-422b-8ebe-3c4cfcabae9b",
                            "https://firebasestorage.googleapis.com/v0/b/nomoneytrip-63056.appspot.com/o/2.PNG?alt=media&token=874f89c0-d134-4981-8be4-4f690deaf510"
                        ),
                        favoriteList = ArrayList(),
                        internet = true,
                        market = true,
                        optionList = listOf("농촌 벼농사"+i, "할매 할부지 앞에서 장기자랑"+i),
                        parking = true,
                        phone = "041-944-2007",
                        time = "일손 4시간",
                        title = "초록초록 농촌마을에서 봉사를 같이하시죠",
                        xPos = "36.412677",
                        yPos = "126.830979"
                    )

                )
        }
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.main_nav_host)
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.bottom_nav_1 || destination.id == R.id.bottom_nav_2 || destination.id == R.id.bottom_nav_3 || destination.id == R.id.bottom_nav_4) {
                binding.mainBottomNavigation.visibility = View.VISIBLE
            } else {
                binding.mainBottomNavigation.visibility = View.GONE
            }
        }
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }

}
