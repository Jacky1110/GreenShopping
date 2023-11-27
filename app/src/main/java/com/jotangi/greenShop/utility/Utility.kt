package com.jotangi.greenShop.utility

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Base64
import android.view.LayoutInflater
import android.widget.Toast
import com.jotangi.greenShop.R
import java.lang.ref.WeakReference
import java.nio.charset.StandardCharsets


object Utility {

    private var dialog: Dialog? = null
    private var contextRef: WeakReference<Context>? = null

    fun encodeFileToBase64(string: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                return Base64.encodeToString(string.toByteArray(StandardCharsets.UTF_8), Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return ""
    }

    fun logTitle(str: String): String {
        return "------------------- $str -------------------"
    }

    fun startLoading(cont: Context) {
        contextRef = WeakReference(cont)
        val context = contextRef?.get()
        context?.let {
            val inflater = LayoutInflater.from(it)
            val view = inflater.inflate(R.layout.loading_progress, null)
            dialog = Dialog(it)
            dialog!!.setCancelable(false)
            dialog!!.setContentView(view)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.dismiss()
            dialog!!.show()
        }
    }

    fun endLoading(message: String) {
        val context = contextRef?.get() as? Activity
        context?.runOnUiThread {
            dialog?.dismiss()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun endLoading() {
        val context = contextRef?.get() as? Activity
        context?.runOnUiThread {
            dialog?.dismiss()
        }
    }
}