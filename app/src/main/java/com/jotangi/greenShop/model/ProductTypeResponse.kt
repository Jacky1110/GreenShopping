package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class ProductTypeResponse(

    @SerializedName("pid")
    val pid: String,

    @SerializedName("product_type")
    val productType: String,

    @SerializedName("producttype_name")
    val productTypeName: String,

    @SerializedName("producttype_picture")
    val productTypePicture: String

)