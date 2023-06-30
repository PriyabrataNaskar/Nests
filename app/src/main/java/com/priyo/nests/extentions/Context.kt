package com.priyo.nests.extentions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat

fun Context.getDrawableCompat(drawableResId: Int?): Drawable? {
    if (drawableResId == null) return null
    return ResourcesCompat.getDrawable(
        this.resources,
        drawableResId,
        null,
    )
}
