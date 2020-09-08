package com.mtjin.nomoneytrip.views

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.utils.convertTimestampToPointFullDate
import com.mtjin.nomoneytrip.utils.convertTimestampToTerm
import com.mtjin.nomoneytrip.views.home.HomeHashTagAdapter
import com.mtjin.nomoneytrip.views.home.HomeProductAdapter
import com.mtjin.nomoneytrip.views.home.ProductHashTagAdapter
import com.mtjin.nomoneytrip.views.localpage.LocalPageAdapter
import com.mtjin.nomoneytrip.views.localpage.LocalProductAdapter
import java.util.concurrent.TimeUnit

@BindingAdapter("urlImage")
fun ImageView.setUrlImage(url: String) {
    Glide.with(this).load(url)
        .thumbnail(0.1f)
        .error(R.drawable.img_product)
        .into(this)
}

@BindingAdapter("setRating")
fun RatingBar.setMovieRating(score: String) {
    rating = (score.toFloatOrNull() ?: 0f)
}


//9.6~9.8
@BindingAdapter("startTimestampTerm", "endTimestampTerm")
fun TextView.setTimestampTerm(startTimestamp: Long, endTimestamp: Long) {
    text = convertTimestampToTerm(startTimestamp, endTimestamp)
}

//timestamp -> 2020.01.02
@BindingAdapter("timestampPointFullDate")
fun TextView.setTimestampPointFullDate(timestamp: Long) {
    text = timestamp.convertTimestampToPointFullDate()
}

// 2박, 일손 8시간
@BindingAdapter("startTimestamp", "endTimestamp", "time")
fun TextView.setDayTime(startTimestamp: Long, endTimestamp: Long, time: String) {
    var time1 = startTimestamp
    var time2 = endTimestamp
    val MILLIS_PER_DAY = 1000 * 60 * 60 * 24.toLong()
    time1 -= time1 % MILLIS_PER_DAY
    time2 -= time2 % MILLIS_PER_DAY
    val day = TimeUnit.DAYS.convert(time1 - time2, TimeUnit.MILLISECONDS).toString()
    text = (day + "박, 일손 " + time + "시간")
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
    }
}