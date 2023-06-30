package com.priyo.nests.extentions

import com.google.android.material.textview.MaterialTextView

// todo: move to core ui
fun MaterialTextView.setDrawableStartAndEnd(startDrawableResId: Int?, endDrawableResourceId: Int?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(
        this.context.getDrawableCompat(startDrawableResId),
        null,
        this.context.getDrawableCompat(endDrawableResourceId),
        null,
    )
}

fun MaterialTextView.setDrawableStart(drawableResId: Int) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(
        this.context.getDrawableCompat(drawableResId),
        null,
        null,
        null,
    )
}
