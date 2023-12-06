package com.jotangi.greenShop.api

import com.jotangi.greenShop.model.BannerListVO
import com.jotangi.greenShop.model.LoginResponse
import com.jotangi.greenShop.model.ModifyPersonDataResponse
import com.jotangi.greenShop.model.PackageListResponse
import com.jotangi.greenShop.model.PersonDataResponse
import com.jotangi.greenShop.model.ProductListResponse
import com.jotangi.greenShop.model.ProductTypeResponse
import com.jotangi.greenShop.model.UpLoadImagResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface ApiConfig {

    companion object {
        // 正式機
        const val URL_HOST = "https://rilink.com.tw/"

        // 測試機
//        const val URL_HOST = "https://rilink.jotangi.com.tw:11074/"

        // 正式機(付款)
        const val PAY_URL = "https://rilink.com.tw/ticketec/ecpay/ecpayindex.php?orderid="

        // 測試機(付款)
//        const val PAY_URL = "https://rilink.jotangi.com.tw:11074/ticketec/ecpay/ecpayindex.php?orderid="

        // 正式機(租車)
        const val CAR_URL = "https://rilink.com.tw/api/v1/"

        // 測試機(租車)
//        const val CAR_URL = "https://rilink.jotangi.com.tw:11074/api/v1"

        // 照片上傳
        const val MEMBER_IMG_URL = "$CAR_URL/personimages/"

        // 會員密碼變更
        const val MALL_REWRITE_PWD = "https://ks-api.jotangi.net/api/auth/rewritepwd"

        const val API_CODE_SUCCESS = "0x0200"
        const val API_CODE_0x0201 = "0x0201"
        const val API_CODE_0x0202 = "0x0202"
        const val API_CODE_0x0203 = "0x0203"
        const val API_CODE_0x0204 = "0x0204"
        const val API_CODE_0x0206 = "0x0206"
        const val API_CODE_101 = "101"
    }

    // 登入
    @POST("ticketec/api/user_login.php")
    @FormUrlEncoded
    fun apiLogin(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String
    ): Call<LoginResponse>


    // 新增取得banner資訊
    @POST("ticketec/api/banner_list.php")
    @FormUrlEncoded
    fun apiBannerList(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String
    ): Call<List<BannerListVO>>


    // 綠悠遊商城/租車取得會員詳細資料
    @POST("account/getpersondata")
    @FormUrlEncoded
    fun apiGetPersonData(
        @Field("account") account: String,
        @Field("accountType") accountType: String,
        @Field("pw") pw: String,
    ): Call<PersonDataResponse>


    // 綠悠遊商城/租車修改會員詳細資料
    @POST("account/modifypersondata")
    @FormUrlEncoded
    fun apiModifypersondata(
        @Field("account") account: String,
        @Field("accountType") accountType: String,
        @Field("Pw") pw: String,
        @Field("Tel") tel: String,
        @Field("name") name: String,
        @Field("Sex") sex: String,
        @Field("City") city: String,
        @Field("region") region: String,
        @Field("birthday") birthday: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("referrerPhone") referrerPhone: String,
    ): Call<ModifyPersonDataResponse>


    // 上傳會員照片
    @POST("account/uploadimage")
    @Multipart
    fun apiUpLoadImag(
        @Part("account") account: String,
        @Part("accountType") accountType: String,
        @Part("Pw") pw: String,
        @Part("imgtitle") imgtitle: String,
        @Part cmdImageFile: MultipartBody.Part,
    ): Call<UpLoadImagResponse>


    // 商城套票列表
    @POST("ticketec/api/package_list.php")
    @FormUrlEncoded
    fun apiGetPackageList(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String,
    ): Call<List<PackageListResponse>>

    // 商城商品類別
    @POST("ticketec/api/product_type.php")
    @FormUrlEncoded
    fun apiGetProductType(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String,
    ): Call<List<ProductTypeResponse>>


    // 商城商品列表
    @POST("ticketec/api/product_list.php")
    @FormUrlEncoded
    fun apiGetProductList(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String,
        @Field("product_type") productType: String,
    ): Call<List<ProductListResponse>>
}