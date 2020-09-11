package com.mtjin.nomoneytrip.utils

const val TAG = "LOG_TAG"

var FCM_KEY: String =
    "AAAALELZg6I:APA91bGJ2xRqylwmLpnsKGDPhfKh9JRBU5cqTenTlZuCKfphWOjuft10tlouHuSfN4QUJjJ0dyyayVhkOEITr8kGHzN3oAvW5Hipn5UlhcF7GDaUE1XmnL9MKImvbjOtdAvcEiG-UAgy"

var TOUR_API_KEY: String =
    "GU7pI9Qe5kD1WOmPjAuxSGYzbLGkUJXMnQmy8EyV18c%2FdMkdvBBO1gpgrNoX30AOF%2FyKVxQkdZ45c7hMLOBfzQ%3D%3D"

const val EXTRA_NOTIFICATION_TITLE = "EXTRA_NOTIFICATION_TITLE"
const val EXTRA_NOTIFICATION_MESSAGE = "EXTRA_NOTIFICATION_MESSAGE"

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
var uuid = "" // 유저 고유 토큰
var fcm = "" // fcm

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
const val TIMESTAMP = "timestamp"

// 시간
const val TIMESTAMP_PER_DAY = 86400000

//에러
const val ERR_DUPLICATE_DATE = "ERR_DUPLICATE_DATE"
const val ERR_UPLOAD_IMAGE = "ERR_UPLOAD_IMAGE"
const val ERR_DUPLICATE_NAME = "ERR_DUPLICATE_NAME"
