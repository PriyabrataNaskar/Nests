package com.priyo.nests.core.extentions

val String?.toIntOrZero: Int get() = this?.toIntOrNull() ?: 0
