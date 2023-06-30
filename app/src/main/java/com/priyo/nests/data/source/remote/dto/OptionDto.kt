package com.priyo.nests.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class OptionDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: String?,
)
