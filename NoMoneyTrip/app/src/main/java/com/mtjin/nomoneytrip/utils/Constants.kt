package com.mtjin.nomoneytrip.utils

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

const val TAG = "LOG_TAG"
const val APP_RULE_URL = "https://blog.naver.com/kjhumal94/222094541233"
const val APP_PERSONAL_INFO_RULE = "https://blog.naver.com/kjhumal94/222094541233"
const val APP_LOCATION_INFO_RULE = "https://blog.naver.com/kjhumal94/222094541884"
var FCM_KEY: String =
    "AAAAfpllS3k:APA91bG1UIo_4vhP-q5H83_N-zdnNXnM4wzdtW2hbLNJdh-qJKeVl5BpYvAsvC4iNg-55VWUwfSebiV5v3obFxSUuuMIp8nHQFrX8B3dojCV1_5i2FWbpmPkSkHCv8ocA_yXZ-Xsy63e"

var TOUR_API_KEY: String =
    "GU7pI9Qe5kD1WOmPjAuxSGYzbLGkUJXMnQmy8EyV18c%2FdMkdvBBO1gpgrNoX30AOF%2FyKVxQkdZ45c7hMLOBfzQ%3D%3D"

const val EXTRA_NOTIFICATION_TITLE = "EXTRA_NOTIFICATION_TITLE"
const val EXTRA_NOTIFICATION_MESSAGE = "EXTRA_NOTIFICATION_MESSAGE"
const val EXTRA_ALARM_PRODUCT_ID = "EXTRA_ALARM_PRODUCT_ID"
const val EXTRA_ALARM_USER_ID = "EXTRA_ALARM_USER_ID"
const val EXTRA_ALARM_TIMESTAMP = "EXTRA_ALARM_TIMESTAMP"
const val EXTRA_ALARM_CASE = "EXTRA_ALARM_CASE"
const val EXTRA_RESERVATION_ID = "EXTRA_RESERVATION_ID"

//reservation state case
const val RESERVATION_DEFAULT_CODE = 0
const val RESERVATION_MASTER_DENY_CODE = 1
const val RESERVATION_MASTER_ACCEPT_CODE = 2
const val RESERVATION_USER_DENY_CODE = 3

//alarm case
const val ALARM_RESERVATION_COMPLETE_CASE1 = 1 //사용자예약 완료
const val ALARM_RESERVATION_ACCEPT_CASE2 = 2 //이장님 수락
const val ALARM_START_CASE3 = 3 //여행하루전알람
const val ALARM_REVIEW_CASE4 = 4 // 여행끝 리뷰 알람
const val ALARM_RESERVATION_REQUEST_CASE5 = 5 // 여행예약 이장님께 알림
const val ALARM_RESERVATION_DENY_CASE5 = 6 // 여행예약 이장님께 알림

// area code
const val SEOUL_CODE = 1
const val INCHEON_CODE = 2
const val DAEJEON_CODE = 3
const val DAEGU_CODE = 4
const val GWANGJU_CODE = 5
const val BUSAN_CODE = 6
const val ULSAN_CODE = 7
const val SEJONG_CODE = 8
const val GYEUNGI_CODE = 31
const val KANGWON_CODE = 32
const val CHOONGBUK_CODE = 33
const val CHOONGNAM_CODE = 34
const val GYUNGBUK_CODE = 35
const val GYUNGNAM_CODE = 36
const val JEONBUK_CODE = 37
const val JEONNAM_CODE = 38
const val JEJU_CODE = 39

// Code
const val RC_PICK_IMAGE = 1001

// user
@Volatile
var uuid = Firebase.auth.currentUser?.uid.toString() // 유저 고유 토큰
var fcm = "" // fcm
var masterProductId = "" // 이장님 상품 아이디

//database child
const val USER = "user" // 유저프로필
const val PRODUCT = "product" // 상품
const val CITY = "city" // 지역
const val ID = "id" // 아이디
const val FCM = "fcm" // fcm
const val RESERVATION = "reservation"
const val USER_ID = "userId"
const val PRODUCT_ID = "productId"
const val REVIEW = "review"
const val REVIEWED = "reviewed"
const val NAME = "name"
const val IMAGE = "image"
const val RECOMMEND_LIST = "recommendList"
const val FAVORITE_LIST = "favoriteList"
const val RATING_LIST = "ratingList"
const val ALARM = "alarm"
const val TEL = "tel"
const val MASTER_USER = "masterUser"
const val STATE = "state"

// 시간
const val TIMESTAMP_PER_DAY = 86400000

//에러
const val ERR_DUPLICATE_DATE = "ERR_DUPLICATE_DATE"
const val ERR_UPLOAD_IMAGE = "ERR_UPLOAD_IMAGE"
const val ERR_DUPLICATE_NAME = "ERR_DUPLICATE_NAME"
