package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class ProductListResponse(

    @SerializedName("pid")
    val pid: String,

    @SerializedName("product_no")
    val productNo: String,

    @SerializedName("product_type")
    val productType: String,

    @SerializedName("producttype_name")
    val productTypeName: String,

    @SerializedName("product_name")
    val productName: String,

    @SerializedName("product_price")
    val productPrice: String,

    @SerializedName("product_picture")
    val productPicture: String,

    @SerializedName("product_status")
    val productStatus: String,

    @SerializedName("product_stock")
    val productStock: String,
)
