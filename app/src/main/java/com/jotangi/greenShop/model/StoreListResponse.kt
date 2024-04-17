package com.jotangi.greenShop.model

import com.google.gson.annotations.SerializedName

data class StoreListResponse(

    @SerializedName("sid")
    val sid: String,

    @SerializedName("store_id")
    val storeId: String,

    @SerializedName("store_type")
    val storeType: String,

    @SerializedName("store_name")
    val storeName: String,

    @SerializedName("member_acc")
    val memberAcc: String,

    @SerializedName("member_pwd")
    val memberPwd: String,

    @SerializedName("store_address")
    val storeAddress: String,

    @SerializedName("store_phone")
    val storePhone: String,

    @SerializedName("store_email")
    val storeEmail: String,

    @SerializedName("store_descript")
    val storeDescript: String,

    @SerializedName("store_opentime")
    val storeOpenTime: String,

    @SerializedName("store_picture")
    val storePicture: String,

    @SerializedName("last_logintime")
    val lastLoginTime: String,

    @SerializedName("store_status")
    val storeStatus: String,

    @SerializedName("store_created_at")
    val storeCreatedAt: String,

    @SerializedName("store_updated_at")
    val storeUpdatedAt: String,

    @SerializedName("store_trash")
    val storeTrash: String,

    @SerializedName("fixmotor")
    val fixmotor: String,

    @SerializedName("notification_token")
    val notificationToken: String,

    @SerializedName("isnotify")
    val isnotify: String,

    @SerializedName("stid")
    val stid: String,

    @SerializedName("storetype_name")
    val storeTypeName: String,

    @SerializedName("trash")
    val trash: String
)
