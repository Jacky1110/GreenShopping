package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class ProductType1VO(

    @SerializedName("mid")
    val mid: String,

    @SerializedName("product_type")
    val productType: String,

    @SerializedName("producttype_name")
    val productTypeName: String,

    var isSelected: Boolean = false

)
