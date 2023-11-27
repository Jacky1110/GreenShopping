package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class UpLoadImagResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("returnCode")
    val returnCode: String,

    @SerializedName("errorMsg")
    val errorMsg: String,
)
