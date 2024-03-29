package com.jotangi.greenShop.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import com.jotangi.greenShop.api.AppConfig

object AppUtility {

    private fun getSpref(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            AppConfig.spref,
            Context.MODE_PRIVATE
        )
    }

    fun updateLoginStatus(
        context: Context,
        isLogin: Boolean
    ) {
        getSpref(context).edit()
            .putBoolean(
                AppConfig.IS_LOGIN,
                isLogin
            )
            .apply()
    }

    fun updateLoginId(
        context: Context,
        loginId: String
    ) {
        getSpref(context).edit()
            .putString(
                AppConfig.LOGIN_ID,
                loginId
            )
            .apply()
    }

    fun getLoginId(context: Context): String? {
        return getSpref(context).getString(
            AppConfig.LOGIN_ID,
            ""
        )
    }

    fun updateLoginPassword(
        context: Context,
        loginPw: String
    ) {
        getSpref(context).edit()
            .putString(
                AppConfig.LOGIN_PW,
                loginPw
            )
            .apply()
    }

    fun getLoginPassword(context: Context): String? {
        return getSpref(context).getString(
            AppConfig.LOGIN_PW,
            ""
        )
    }

    fun updatePersonName(
        context: Context,
        personName: String
    ) {
        getSpref(context).edit()
            .putString(
                AppConfig.PERSON_NAME,
                personName
            )
            .apply()
    }


    fun getPersonName(context: Context): String? {
        return getSpref(context).getString(
            AppConfig.PERSON_NAME,
            ""
        )
    }

    fun updatePersonPoint(
        context: Context,
        personPoint: String
    ) {
        getSpref(context).edit()
            .putString(
                AppConfig.PERSON_POINT,
                personPoint
            )
            .apply()
    }

    fun getPersonPoint(context: Context): String? {
        return getSpref(context).getString(
            AppConfig.PERSON_POINT,
            ""
        )
    }

    fun updateTypePosition(
        context: Context,
        typePosition: Int
    ) {
        getSpref(context).edit()
            .putInt(
                AppConfig.TYPE_POSITION,
                typePosition
            )
            .apply()
    }

    fun getTypePosition(context: Context): Int {
        return getSpref(context).getInt(
            AppConfig.TYPE_POSITION,
            0
        )
    }



    fun showPopDialog(
        context: Context,
        code: String?,
        message: String?
    ) {
//        val serverMessage = when (code) {
//            ApiConfig.API_CODE_0x0201 -> "已註冊成功"
//            ApiConfig.API_CODE_0x0202 -> context.resources.getString(R.string.signup_response_202)
//            ApiConfig.API_CODE_0x0203 -> context.resources.getString(R.string.signup_response_203)
//            ApiConfig.API_CODE_0x0204 -> context.resources.getString(R.string.signup_response_204)
//            ApiConfig.API_CODE_0x0206 -> context.resources.getString(R.string.signup_response_206)
//            ApiConfig.API_CODE_NOT_FOUND -> context.resources.getString(R.string.public_response_404)
//            null -> message
//            else -> message
//        }

        val alert = AlertDialog.Builder(context)

        alert.setTitle("提醒")
        alert.setMessage(message)
        alert.setPositiveButton("確定") { _, _ ->

        }

        alert.show()
    }
}