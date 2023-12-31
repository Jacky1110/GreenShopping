package com.jotangi.greenShop

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jotangi.greenShop.api.ApiConfig
import com.jotangi.greenShop.model.BannerListVO
import com.jotangi.greenShop.model.LoginResponse
import com.jotangi.greenShop.model.ModifyPersonDataResponse
import com.jotangi.greenShop.model.PackageListResponse
import com.jotangi.greenShop.model.PersonDataResponse
import com.jotangi.greenShop.model.ProductListResponse
import com.jotangi.greenShop.model.ProductTypeResponse
import com.jotangi.greenShop.model.UpLoadImagResponse
import com.jotangi.greenShop.utility.ApiUtility
import com.jotangi.greenShop.utility.AppUtility
import com.jotangi.greenShop.utility.Utility
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainViewModel : ViewModel() {

    //登入
    private val _loginData: MutableLiveData<LoginResponse> by lazy {
        MutableLiveData<LoginResponse>()
    }
    val loginData: LiveData<LoginResponse> get() = _loginData


    //banner
    private val _bannerData: MutableLiveData<List<BannerListVO>> by lazy {
        MutableLiveData<List<BannerListVO>>()
    }

    val bannerData: LiveData<List<BannerListVO>> get() = _bannerData

    // 綠悠遊商城/租車修改會員詳細資料
    private val _modifyPersonData: MutableLiveData<ModifyPersonDataResponse> by lazy {
        MutableLiveData<ModifyPersonDataResponse>()
    }

    val modifyPersonData: LiveData<ModifyPersonDataResponse> get() = _modifyPersonData


    // 取得會員資料
    private val _personData: MutableLiveData<PersonDataResponse> by lazy {
        MutableLiveData<PersonDataResponse>()
    }

    val personData: LiveData<PersonDataResponse> get() = _personData


    // 上傳會員照片
    private val _upLoadImagData: MutableLiveData<UpLoadImagResponse> by lazy {
        MutableLiveData<UpLoadImagResponse>()
    }

    val upLoadImagData: LiveData<UpLoadImagResponse> get() = _upLoadImagData


    // 商城套票列表
    private val _packageListData: MutableLiveData<List<PackageListResponse>> by lazy {
        MutableLiveData<List<PackageListResponse>>()
    }

    val packageListData: LiveData<List<PackageListResponse>> get() = _packageListData


    // 商城商品類別
    private val _productTypeData: MutableLiveData<List<ProductTypeResponse>> by lazy {
        MutableLiveData<List<ProductTypeResponse>>()
    }

    val productTypeData: LiveData<List<ProductTypeResponse>> get() = _productTypeData


    // 商城商品列表
    private val _productListData: MutableLiveData<List<ProductListResponse>> by lazy {
        MutableLiveData<List<ProductListResponse>>()
    }

    val productListData: LiveData<List<ProductListResponse>> get() = _productListData


    fun clearData() {
        _loginData.value = null
        _personData.value = null

    }


    fun login(
        context: Context,
        memberId: String,
        memberPassword: String
    ) {
        val call: Call<LoginResponse> = ApiUtility.service.apiLogin(
            memberId,
            memberPassword
        )
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    if (
                        response.body()!!.code == ApiConfig.API_CODE_0x0201 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0202 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0203 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0204 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0206
                    ) {

                        _loginData.value = response.body()

                    } else {

                        _loginData.value = response.body()
                    }
                } else {


                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                AppUtility.showPopDialog(
                    context,
                    null,
                    ApiUtility.apiFailureMessage(call, t)
                )
            }
        })
    }


    fun getMainBannerData(
        context: Context,
        memberId: String,
        memberPassword: String
    ) {
        val call: Call<List<BannerListVO>> = ApiUtility.service.apiBannerList(
            memberId,
            memberPassword
        )

        call.enqueue(object : Callback<List<BannerListVO>> {

            override fun onResponse(
                call: Call<List<BannerListVO>>,
                response: Response<List<BannerListVO>>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _bannerData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<BannerListVO>>, t: Throwable) {
                _bannerData.value = null
            }
        })
    }


    // 綠悠遊商城/租車修改會員詳細資料
    fun getModifyPersonData(
        context: Context,
        memberId: String,
        accountType: String,
        memberPassword: String,
        tel: String,
        name: String,
        sex: String,
        city: String,
        region: String,
        birthday: String,
        email: String,
        address: String,
        referrerPhone: String,

        ) {
        val call: Call<ModifyPersonDataResponse> = ApiUtility.service2.apiModifypersondata(
            Utility.encodeFileToBase64(memberId),
            Utility.encodeFileToBase64(accountType),
            Utility.encodeFileToBase64(memberPassword),
            Utility.encodeFileToBase64(tel),
            Utility.encodeFileToBase64(name),
            Utility.encodeFileToBase64(sex),
            Utility.encodeFileToBase64(city),
            Utility.encodeFileToBase64(region),
            Utility.encodeFileToBase64(birthday),
            Utility.encodeFileToBase64(email),
            Utility.encodeFileToBase64(address),
            Utility.encodeFileToBase64(referrerPhone),
        )

        call.enqueue(object : Callback<ModifyPersonDataResponse> {

            override fun onResponse(
                call: Call<ModifyPersonDataResponse>,
                response: Response<ModifyPersonDataResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _modifyPersonData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<ModifyPersonDataResponse>, t: Throwable) {
                _bannerData.value = null
            }
        })
    }


    // 取得會員資料
    fun getPersonData(
        context: Context,
        memberId: String,
        accountType: String,
        memberPassword: String,
    ) {
        val call: Call<PersonDataResponse> = ApiUtility.service2.apiGetPersonData(
            Utility.encodeFileToBase64(memberId),
            Utility.encodeFileToBase64(accountType),
            Utility.encodeFileToBase64(memberPassword),
        )

        Log.d("TAG", "memberId: ${Utility.encodeFileToBase64(memberId)}")
        Log.d("TAG", "accountType: ${Utility.encodeFileToBase64(accountType)}")
        Log.d("TAG", "memberPassword: ${Utility.encodeFileToBase64(memberPassword)}")

        call.enqueue(object : Callback<PersonDataResponse> {

            override fun onResponse(
                call: Call<PersonDataResponse>,
                response: Response<PersonDataResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _personData.value = response.body()
                    Log.d("TAG", "personData: ${_personData.value}")
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<PersonDataResponse>, t: Throwable) {
                _personData.value = null
            }
        })
    }


    // 上傳會員照片
    fun putUpLoadImag(
        context: Context,
        memberId: String,
        accountType: String,
        memberPassword: String,
        imgtitle: String,
        cmdImageFile: File,

        ) {

        val mediaType = "image/jpg".toMediaType()
        val requestFile = RequestBody.create(mediaType, cmdImageFile)
        val imageFile =
            MultipartBody.Part.createFormData("cmdImageFile", cmdImageFile.name, requestFile)

        val account =
            Utility.encodeFileToBase64(memberId).toRequestBody("multipart/form-data".toMediaType())
        val type = Utility.encodeFileToBase64(accountType)
            .toRequestBody("multipart/form-data".toMediaType())
        val pw = Utility.encodeFileToBase64(memberPassword)
            .toRequestBody("multipart/form-data".toMediaType())
        val imgTitle =
            Utility.encodeFileToBase64(imgtitle).toRequestBody("multipart/form-data".toMediaType())

        val call: Call<UpLoadImagResponse> = ApiUtility.service2.apiUpLoadImag(
            Utility.encodeFileToBase64(memberId),
            Utility.encodeFileToBase64(accountType),
            Utility.encodeFileToBase64(memberPassword),
            Utility.encodeFileToBase64(imgtitle),
            imageFile

        )

        Log.d("TAG", "memberId: $memberId")
        Log.d("TAG", "accountType: $accountType")
        Log.d("TAG", "memberPassword: $memberPassword")
        Log.d("TAG", "imgtitle: $imgtitle")
        Log.d("TAG", "cmdImageFile: ${imageFile}")

        call.enqueue(object : Callback<UpLoadImagResponse> {

            override fun onResponse(
                call: Call<UpLoadImagResponse>,
                response: Response<UpLoadImagResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _upLoadImagData.value = response.body()
                    Log.d("TAG", "personData: ${_upLoadImagData.value}")
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<UpLoadImagResponse>, t: Throwable) {
                _personData.value = null
            }
        })
    }

    // 商城套票列表
    fun getPackageListData(
        context: Context,
        memberId: String,
        memberPassword: String
    ) {
        val call: Call<List<PackageListResponse>> = ApiUtility.service.apiGetPackageList(
            memberId,
            memberPassword
        )

        call.enqueue(object : Callback<List<PackageListResponse>> {

            override fun onResponse(
                call: Call<List<PackageListResponse>>,
                response: Response<List<PackageListResponse>>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _packageListData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<PackageListResponse>>, t: Throwable) {
                _packageListData.value = null
            }
        })
    }

    // 商城商品類別
    fun getProductTypeData(
        context: Context,
        memberId: String,
        memberPassword: String
    ) {
        val call: Call<List<ProductTypeResponse>> = ApiUtility.service.apiGetProductType(
            memberId,
            memberPassword
        )

        call.enqueue(object : Callback<List<ProductTypeResponse>> {

            override fun onResponse(
                call: Call<List<ProductTypeResponse>>,
                response: Response<List<ProductTypeResponse>>
            ) {

                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _productTypeData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<ProductTypeResponse>>, t: Throwable) {
                _productTypeData.value = null
            }
        })

    }

    // 商城商品列表
    fun getProductListData(
        context: Context,
        memberId: String,
        memberPassword: String,
        productType: String
    ) {
        val call: Call<List<ProductListResponse>> = ApiUtility.service.apiGetProductList(
            memberId,
            memberPassword,
            productType
        )

        call.enqueue(object : Callback<List<ProductListResponse>> {

            override fun onResponse(
                call: Call<List<ProductListResponse>>,
                response: Response<List<ProductListResponse>>
            ) {

                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _productListData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<ProductListResponse>>, t: Throwable) {
                _productListData.value = null
            }
        })

    }


}