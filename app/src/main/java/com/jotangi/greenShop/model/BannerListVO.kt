package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class BannerListVO(


    @SerializedName("banner_subject")
    val bannerSubject: String,

    @SerializedName("banner_date")
    val bannerDate: String,

    @SerializedName("banner_enddate")
    val bannerEnddate: String,

    @SerializedName("banner_descript")
    val bannerDescript: String,

    @SerializedName("banner_picture")
    val bannerPicture: String,

    @SerializedName("banner_link")
    val bannerLink: String,
)
