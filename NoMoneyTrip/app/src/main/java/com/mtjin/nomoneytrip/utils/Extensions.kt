package com.mtjin.nomoneytrip.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun Context.getMyDrawable(id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}