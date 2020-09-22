package com.mtjin.nomoneytrip.views

import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.utils.*
import com.mtjin.nomoneytrip.views.alarm.AlarmAdapter
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
import com.mtjin.nomoneytrip.views.home.HomeHashTagAdapter
import com.mtjin.nomoneytrip.views.home.HomeProductAdapter
import com.mtjin.nomoneytrip.views.home.ProductHashTagAdapter
import com.mtjin.nomoneytrip.views.localpage.LocalPageAdapter
import com.mtjin.nomoneytrip.views.localpage.LocalProductAdapter
import com.mtjin.nomoneytrip.views.master_main.MasterMainAdapter
import com.mtjin.nomoneytrip.views.reservation_history.ReservationHistoryAdapter
import com.mtjin.nomoneytrip.views.tour_history.TourHistoryAdapter
import de.hdodenhof.circleimageview.CircleImageView
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@BindingAdapter("urlImage")
fun ImageView.setUrlImage(url: String) {
    Glide.with(this).load(url)
        .thumbnail(0.1f)
        .error(R.drawable.img_product)
        .into(this)
}

@BindingAdapter("alarmStateImage")
fun CircleImageView.setReadState(readState: Boolean) {
    if (readState) setImageDrawable(context.getMyDrawable(R.drawable.ic_alarm_pic))
    else setImageDrawable(context.getMyDrawable(R.drawable.ic_alarm_pic_2))

}

@BindingAdapter("alarmBackground")
fun View.setAlarmBackground(readState: Boolean) {
    if (readState) setBackgroundColor(context.getMyColor(R.color.colorWhiteFDFD))
    else setBackgroundColor(context.getMyColor(R.color.colorWhiteFFF5EF))
}

@BindingAdapter("masterState")
fun View.setMasterState(masterState: Int) {
    if (masterState != 0) visibility = View.GONE
    else visibility = View.VISIBLE
}

//예약취소 보이기 유무
@BindingAdapter("reservationVisibility")
fun TextView.setReservationVisibility(reservationProduct: ReservationProduct) {
    when {
        reservationProduct.reservation.state == 3 -> { //사용자 취소한 경우
            visibility = View.GONE
        }
        reservationProduct.reservation.state == 1 -> { //이장님 거절한 경우
            visibility = View.GONE
        }
        reservationProduct.reservation.state == 2 && reservationProduct.reservation.startDateTimestamp >= getTimestamp() -> {//이장님 수락하고 아직 여행전인 경우
            visibility = View.VISIBLE
        }
        reservationProduct.reservation.endDateTimestamp < getTimestamp() -> { //이미 여행 끝난 경우
            visibility = View.GONE
        }
        else -> {
            visibility = View.VISIBLE
        }
    }
}

//예약상태별 텍스트
@BindingAdapter("reservationStateText")
fun TextView.setReservationStateText(reservationProduct: ReservationProduct) {
    when {
        reservationProduct.reservation.state == 3 -> { //사용자 취소한 경우
            text = "예약이 취소되었습니다."
            setTextColor(context.getMyColor(R.color.colorRedEF4550))
        }
        reservationProduct.reservation.state == 1 -> { //이장님 거절한 경우
            text = "이장님이 예약을 거절했습니다."
            setTextColor(context.getMyColor(R.color.colorRedEF4550))
        }
        reservationProduct.reservation.state == 2 && reservationProduct.reservation.startDateTimestamp >= getTimestamp() -> {//이장님 수락하고 아직 여행전인 경우
            text = "예약이 확정되었습니다."
            setTextColor(context.getMyColor(R.color.colorOrangeF79256))
        }
        reservationProduct.reservation.endDateTimestamp < getTimestamp() -> { //이미 여행 끝난 경우
            text = "다녀온 여행입니다."
            setTextColor(context.getMyColor(R.color.colorOrangeF79256))
        }
        else -> {
            text = "예약 승인을 기다리고 있습니다."
            setTextColor(context.getMyColor(R.color.colorOrangeF79256))
        }
    }
}

@BindingAdapter("urlImageRadius")
fun ImageView.setUrlImageRadius(url: String) {
    Glide.with(this).load(url)
        .thumbnail(0.1f)
        .transform(RoundedCorners(16))
        .into(this)
}

@BindingAdapter("rating")
fun RatingBar.setMovieRating(score: String) {
    rating = (score.toFloatOrNull() ?: 0f)
}

@BindingAdapter("ratingListAverage")
fun RatingBar.setRatingListAverage(ratingList: List<Float>) {
    rating = if (ratingList.isNotEmpty()) {
        val average = ratingList.sum() / ratingList.size
        val score: Float = ((average * 10.0).roundToInt() / 10.0).toFloat()
        score
    } else {
        0f
    }
}

@BindingAdapter("ratingListAverageText")
fun TextView.setRatingListAverageText(ratingList: List<Float>) {
    text = if (ratingList.isNotEmpty()) {
        val average = ratingList.sum() / ratingList.size
        val score = ((average * 10.0).roundToInt() / 10.0).toString()
        score
    } else {
        "" + 0
    }
}

// num -> 외 1명, 1명일 경우는 안보이게
@BindingAdapter("numHowManyExceptMe")
fun TextView.setNumHowManyExceptMe(num: String) {
    if (num == "1" || num == "0") {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
        text = ("외 " + num + "명")
    }
}


//9.6~9.8
@BindingAdapter("startTimestampTerm", "endTimestampTerm")
fun TextView.setTimestampTerm(startTimestamp: Long, endTimestamp: Long) {
    text = convertTimestampToTerm(startTimestamp, endTimestamp)
}

//09.06 - 09.08
@BindingAdapter("startTimestampOnlyDateMinusTerm", "endTimestampOnlyDateMinusTerm")
fun TextView.setTimestampOnlyDateMinusTerm(startTimestamp: Long, endTimestamp: Long) {
    text = convertTimestampOnlyDateMinusTerm(startTimestamp, endTimestamp)
}

//timestamp -> 2020.01.02
@BindingAdapter("timestampPointFullDate")
fun TextView.setTimestampPointFullDate(timestamp: Long) {
    text = timestamp.convertTimestampToPointFullDate()
}

//timestamp -> 01.02
@BindingAdapter("timestampSlashOnlyDate")
fun TextView.setTimestampPointOnlyDate(timestamp: Long) {
    text = timestamp.convertTimestampToSlashOnlyDate()
}

//timestamp -> 2020.01.02 23:00
@BindingAdapter("timestampPointFullDateTime")
fun TextView.setTimestampPointFullDateTime(timestamp: Long) {
    text = timestamp.convertTimestampToPointFullDateTime()
}

// timestamp(1599663600000), time(일손 4시간) -> 2박, 일손 8시간
@BindingAdapter("startTimestamp", "endTimestamp", "time")
fun TextView.setDayTime(startTimestamp: Long, endTimestamp: Long, time: String) {
    var time1 = startTimestamp
    var time2 = endTimestamp
    val MILLIS_PER_DAY = 1000 * 60 * 60 * 24.toLong()
    time1 -= time1 % MILLIS_PER_DAY
    time2 -= time2 % MILLIS_PER_DAY
    val day = TimeUnit.DAYS.convert(time2 - time1, TimeUnit.MILLISECONDS).toString()
    text = (day + "박, " + time)
}

// timestamp(1599663600000) -> 2박
@BindingAdapter("startTimestampNight", "endTimestampNight")
fun TextView.setTimestampNight(startTimestamp: Long, endTimestamp: Long) {
    var time1 = startTimestamp
    var time2 = endTimestamp
    val MILLIS_PER_DAY = 1000 * 60 * 60 * 24.toLong()
    time1 -= time1 % MILLIS_PER_DAY
    time2 -= time2 % MILLIS_PER_DAY
    val day = TimeUnit.DAYS.convert(time2 - time1, TimeUnit.MILLISECONDS).toString()
    text = (day + "박")
}

// 하단 다음버튼 필수선택 여부에 따른 배경 색
@BindingAdapter("onNextBackground")
fun TextView.setOnNextBackground(isCompleted: Boolean) {
    if (isCompleted) setBackgroundColor(context.getMyColor(R.color.colorOrangeF79256))
    else setBackgroundColor(context.getMyColor(R.color.colorGrayC8C8))
}

// 인증하기 배경색
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("onRequestBackground")
fun TextView.setOnRequestBackground(isCompleted: Boolean) {
    if (isCompleted) {
        background = context.getMyDrawable(R.drawable.bg_btn_solid_orange_f792_radius_8dp)
    } else context.getMyDrawable(R.drawable.bg_btn_solid_gray_c8c8_radius_8dp)
}

// 인증하기 화면 인증번호 입력 EditText
@BindingAdapter("onEnterBackground")
fun EditText.setOnEnterBackground(isCompleted: Boolean) {
    background =
        if (isCompleted) context.getMyDrawable(R.drawable.bg_edit_stroke_gray_c8c8_radius_2dp)
        else context.getMyDrawable(R.drawable.bg_edit_solid_gray_f4f4_radius_2dp)
}

@BindingAdapter("setItems")
fun RecyclerView.setAdapterItems(items: List<Any>?) {
    when (adapter) {
        is LocalPageAdapter -> {
            items?.let {
                with(adapter as LocalPageAdapter) {
                    clear()
                    addItems(it as List<TourIntroduce>)
                }
            }
        }
        is HomeProductAdapter -> {
            items?.let {
                with(adapter as HomeProductAdapter) {
                    clear()
                    addItems(it as List<Product>)
                }
            }
        }
        is ProductHashTagAdapter -> {
            items?.let {
                with(adapter as ProductHashTagAdapter) {
                    clear()
                    addItems(it as List<String>)
                }
            }
        }
        is HomeHashTagAdapter -> {
            items?.let {
                with(adapter as HomeHashTagAdapter) {
                    clear()
                    addItems(it as List<String>)
                }
            }
        }
        is LocalProductAdapter -> {
            items?.let {
                with(adapter as LocalProductAdapter) {
                    clear()
                    addItems(it as List<Product>)
                }
            }
        }
        is ReservationHistoryAdapter -> {
            items?.let {
                with(adapter as ReservationHistoryAdapter) {
                    clear()
                    addItems(it as List<ReservationProduct>)
                }
            }
        }
        is TourHistoryAdapter -> {
            items?.let {
                with(adapter as TourHistoryAdapter) {
                    clear()
                    addItems(it as List<ReservationProduct>)
                }
            }
        }
        is CommunityAdapter -> {
            items?.let {
                with(adapter as CommunityAdapter) {
                    clear()
                    addItems(it as List<UserReview>)
                }
            }
        }
        is AlarmAdapter -> {
            items?.let {
                with(adapter as AlarmAdapter) {
                    clear()
                    addItems(it as List<Alarm>)
                }
            }
        }
        is MasterMainAdapter -> {
            items?.let {
                with(adapter as MasterMainAdapter) {
                    clear()
                    addItems(it as List<MasterProduct>)
                }
            }
        }
    }
}