package com.jotangi.greenShop

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jotangi.greenShop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG: String = "${javaClass.simpleName}(TAG)"

    private lateinit var binding: ActivityMainBinding

    private val PERMISSION_REQUEST_CODE = 200

    var isShowCamera = false

    // 拍照錄影、定位、錄音、藍牙
    private val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        checkPermission()
    }


    /**
     * 權限
     */
    private fun checkPermission() {

        if (hasPermissions(*permissions)) {
            isShowCamera = false
            Log.d(TAG, "有權限")
        } else {
            Log.d(TAG, "沒權限")
            requestPermissionsWithExplanation()
        }
    }

    private fun hasPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestPermissionsWithExplanation() {
        val shouldShowRationale = permissions.any {
            ActivityCompat.shouldShowRequestPermissionRationale(this, it)
        }

        if (shouldShowRationale) {
            // 顯示解釋權限的訊息對話框
            showPermissionRationaleDialog()
        } else {
            // 直接請求權限
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
        }
    }

    private fun showPermissionRationaleDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("需要權限")
        dialogBuilder.setMessage("應用程式需要相機、定位等權限以正常運行。請點擊設置以授予權限。")
        dialogBuilder.setPositiveButton("設置") { dialog, _ ->
            dialog.dismiss()
            openAppSettings()
        }
        dialogBuilder.setNegativeButton("取消") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (hasPermissions(*permissions)) {
                isShowCamera = false
                Log.d(TAG, "權限已授予")
            } else {
                Log.d(TAG, "權限被拒絕")
            }
        }
    }


    private fun initView() {

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val navView: BottomNavigationView = binding.navView

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_home -> {
                    navController.navigate(R.id.homeMainFragment)
                }

                R.id.navigation_pointStore -> {

                    navController.navigate(R.id.dynamicTabFragment)
                }

                R.id.navigation_business -> {

                }

                R.id.navigation_member -> {
                    navController.navigate(R.id.memberFragment)
                }

            }


            return@setOnItemSelectedListener true
        }
    }
}