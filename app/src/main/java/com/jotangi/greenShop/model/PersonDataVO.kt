package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class PersonDataVO(

    @SerializedName("account")
    val account: String,

    @SerializedName("accountType")
    val accountType: String,

    @SerializedName("tel")
    val tel: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("sex")
    val sex: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("birthday")
    val birthday: String,

    @SerializedName("imageName")
    val imageName: String,

    @SerializedName("mobileType")
    val mobileType: String,

    @SerializedName("point")
    val point: String,

    @SerializedName("cmdImageFile")
    val cmdImageFile: String,

    @SerializedName("referrerPhone")
    val referrerPhone: String,

    @SerializedName("referrerCount")
    val referrerCount: String,

    @SerializedName("referrerStoreName")
    val referrerStoreName: String,

    @SerializedName("referrerStoreType")
    val referrerStoreType: String

)
