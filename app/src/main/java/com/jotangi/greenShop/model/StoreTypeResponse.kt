package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class StoreTypeResponse(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("createtime")
    val createTime: String,

    @SerializedName("updateTime")
    val updateTime: String,

    @SerializedName("trash")
    val trash: String

)
