package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class BannerListResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("responseMessage")
    val responseMessage: String
)
