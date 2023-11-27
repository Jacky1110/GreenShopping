package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class PackageListResponse(

    @SerializedName("package_no")
    val packageNo: String,

    @SerializedName("product_name")
    val productName: String,

    @SerializedName("product_price")
    val productPrice: String,

    @SerializedName("product_picture")
    val productPicture: String,

    @SerializedName("product_description")
    val productDescription: String,

    @SerializedName("product_status")
    val productStatus: String,

    @SerializedName("product_stock")
    val productStock: String,
)
