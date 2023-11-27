package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class PersonDataResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("returnCode")
    val returnCode: String,

    @SerializedName("returnData")
    val returnData: PersonDataVO

)
