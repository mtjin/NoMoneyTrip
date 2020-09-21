@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.mtjin.nomoneytrip.utils

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

// 날짜만 타임스탬프 변환 2020-01-01 - timestamp
fun String.convertDateToTimestamp(): Long =
    SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(this).time

fun Long.convertTimestampToDate(): String = DateFormat.format("yyyy-MM-dd", this).toString()

fun Long.convertTimestampToPointFullDate(): String =
    DateFormat.format("yyyy.MM.dd", this).toString()

fun Long.convertTimestampToPointFullDateTime(): String =
    DateFormat.format("yyyy.MM.dd HH:mm", this).toString()

fun Long.convertTimestampToSlashOnlyDate(): String =
    DateFormat.format("MM/dd", this).toString()


// 날짜,시간,분 포함된 타임스탬프 변환 2020-01-01-22-30 - timestamp
fun String.convertDateFullToTimestamp(): Long =
    SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.KOREA).parse(this).time

fun Long.convertTimestampToDateFull(): String =
    DateFormat.format("yyyy-MM-dd-HH:mm", this).toString()

fun Long.convertCurrentTimestampToDateTimestamp(): Long =
    this.convertTimestampToDate().convertDateToTimestamp()

// timestamp -> 13:40
fun Long.convertTimestampToTime(): String = DateFormat.format("HH:mm", this).toString()

//fun Long.convertTimesToTimestamp(): Long = DateFormat.format("HH:mm", this).toString()

// 시간 한자리면 앞에 0 붙여주어 변환
fun String.convertHourDoubleDigit(): String = if (this.length < 2) "0$this" else this

// 분 한자리면 앞에 0 붙여주어 반환
fun String.convertMinuteDoubleDigit(): String = if (this.length < 2) "0$this" else this

// 한자리 숫자면 두자리로 변환
fun String.convertSingleToDoubleDigit(): String = if (this.length < 2) "0$this" else this

fun Long.convertTimestampToHour(): Int = DateFormat.format("HH", this).toString().toInt()

fun Long.convertTimestampToMinute(): Int = DateFormat.format("mm", this).toString().toInt()

fun Int.convertNextHour(): Int = if (this == 23) 0 else this + 1

fun Int.convertNextMinute(): Int = if (this == 59) 0 else this + 1

// 현재 Year
fun getCurrentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)

// 현재 Month
fun getCurrentMonth(): Int = Calendar.getInstance().get(Calendar.MONTH) + 1

// 현재 Day
fun getCurrentDay(): Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

// timestamp -> year
fun Long.convertTimestampToYear(): Int {
    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = this
    return cal[Calendar.YEAR]
}

// timestamp -> month
fun Long.convertTimestampToMonth(): Int {
    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = this
    return cal[Calendar.MONTH] + 1
}

// timestamp -> day
fun Long.convertTimestampToDay(): Int {
    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = this
    return cal[Calendar.DAY_OF_MONTH]
}


// 1시간 뒤 타임스탬프
fun Long.convertNextHourTimestamp(): Long = this + (60 * 60 * 1000)

// 2020, 1, 20 -> timestamp
fun convertDateToTimestamp(_year: Int, _month: Int, _day: Int): Long {
    val month = _month.toString().convertSingleToDoubleDigit().toInt()
    val day = _day.toString().convertSingleToDoubleDigit().toInt()
    val date = "$_year-$month-$day"
    return date.convertDateToTimestamp()
}

// timestamp -> 9.6~9.8
fun convertTimestampToTerm(startTimestamp: Long, endTimestamp: Long): String {
    return DateFormat.format("MM-dd", startTimestamp)
        .toString() + "~" + DateFormat.format("MM-dd", endTimestamp).toString()
}

// timestamp -> 9.6~9.8
fun convertTimestampOnlyDateMinusTerm(startTimestamp: Long, endTimestamp: Long): String {
    return DateFormat.format("MM-dd", startTimestamp)
        .toString() + " - " + DateFormat.format("MM-dd", endTimestamp).toString()
}

// FCM 메시지로 사용
fun convertTimeToFcmMessage(date: Long, time: String): String =
    date.convertTimestampToDate() + " " + time + "에 예약이 있습니다."

fun combineTimestamp(x: Long, y: Long) = (x.toString() + y.toString()).toLong()

// 랜덤키값
fun getRandomKey(): Long = Random(System.currentTimeMillis()).nextLong(100000, 999999)

// 타임스탬프
fun getTimestamp(): Long = System.currentTimeMillis()