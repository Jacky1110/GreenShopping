package com.jotangi.greenShop.member

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jotangi.greenShop.BaseFragment
import com.jotangi.greenShop.R
import com.jotangi.greenShop.api.ApiConfig
import com.jotangi.greenShop.api.AppConfig
import com.jotangi.greenShop.databinding.AppBarMainBinding
import com.jotangi.greenShop.databinding.FragmentMemberBinding
import com.jotangi.greenShop.model.PersonDataVO
import com.jotangi.greenShop.utility.AppUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


class MemberFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentMemberBinding

    private var tempImage: Bitmap? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onResume() {
        super.onResume()

        setBnv(3)
    }

    private fun init() {

        initObserver()
        initData()
        initAction()
        initView()
    }

    private fun initView() {

        val navView = mActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.visibility = View.VISIBLE

        val personName = AppUtility.getPersonName(requireContext()).toString()
        val welcomeMember = getString(R.string.welcome_member, personName)
        val welcome = getString(R.string.welcome)

        binding.apply {

            if (AppUtility.getLoginId(requireContext()).toString().isNotEmpty()) {

                tvUserName.text = welcomeMember
                btnEditUserOut.visibility = View.VISIBLE
                btnEditUser.visibility = View.INVISIBLE
                itemAccount.visibility = View.VISIBLE


            } else {

                tvUserName.text = welcome
                btnEditUserOut.visibility = View.INVISIBLE
                btnEditUser.visibility = View.VISIBLE
                itemAccount.visibility = View.GONE
            }
        }


    }


    private fun initObserver() {

        mainViewModel.personData.observe(viewLifecycleOwner) { result ->

            if (result != null) {
                if (result.returnCode == ApiConfig.API_CODE_101) {

                    updateViewData(result.returnData)
                } else {

                    binding.progressBar.visibility = View.GONE
                }
            } else {

                binding.progressBar.visibility = View.GONE

            }
        }

    }

    private fun updateViewData(returnData: PersonDataVO) {

        if (AppUtility.getLoginId(requireContext()).toString().isNotEmpty()) {

            binding.progressBar.visibility = View.GONE

            AppUtility.updatePersonName(
                requireContext(),
                returnData.name
            )

            AppUtility.updatePersonPoint(
                requireContext(),
                returnData.point
            )

            val welcomeMember = getString(R.string.welcome_member, returnData.name)
            binding.tvUserName.text = welcomeMember
            binding.tvRPoint.text = returnData.point

            Glide.with(this).load(ApiConfig.MEMBER_IMG_URL + returnData.cmdImageFile)
                .into(binding.ivHead)
        }


    }

    private fun initData() {

        if (AppUtility.getLoginId(requireContext()).toString().isNotEmpty()){

            binding.progressBar.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO) {
                val personData = async {
                    mainViewModel.getPersonData(
                        requireContext(),
                        AppUtility.getLoginId(requireContext())!!,
                        "0",
                        AppUtility.getLoginPassword(requireContext())!!
                    )
                }

                personData.await()

            }
        }
    }

    private fun initAction() {
        binding.apply {

            btnEditUser.setOnClickListener {
                findNavController().navigate(
                    R.id.action_memberFragment_to_memberLoginFragment
                )
            }

            ivHead.setOnClickListener {

                setImage()
            }

            itemQa.setOnClickListener {

                findNavController().navigate(
                    R.id.action_memberFragment_to_accountQAFragment
                )
            }

            itemCs.setOnClickListener {

                findNavController().navigate(
                    R.id.action_memberFragment_to_accountCustomerFragment
                )
            }

            btnEditUserOut.setOnClickListener {

                AppUtility.updateLoginId(
                    requireContext(),
                    ""
                )

                AppUtility.updateLoginPassword(
                    requireContext(),
                    ""
                )

                AppUtility.updatePersonName(
                    requireContext(),
                    ""
                )

                AppUtility.updatePersonPoint(
                    requireContext(),
                    ""
                )

                mainViewModel.clearData()

                btnEditUserOut.visibility = View.GONE
                btnEditUser.visibility = View.VISIBLE
                itemAccount.visibility = View.GONE

                val welcome = getString(R.string.welcome)
                tvUserName.text = welcome
                tvRPoint.text = "0"
            }
        }
    }

    private fun setImage() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("請選擇圖片來源")
            .setItems(R.array.choose_images_from) { dialog, which ->
                if (which == 0) {
                    if (ActivityCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.CAMERA),
                            AppConfig.REQUEST_CAMERA
                        )
                    } else {
                        requestCamera()
                    }
                } else if (which == 1) {
                    if (ActivityCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            AppConfig.REQUEST_EXTERNAL_STORAGE
                        )
                    } else {
                        requestPick()
                    }
                }
            }
        builder.setNegativeButton("返回") { dialog, which ->
            // 在这里处理返回按钮的点击事件
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun requestCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, AppConfig.REQUEST_CAMERA)
    }

    private fun requestPick() {
        val mUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val mIntent = Intent(Intent.ACTION_PICK, mUri)
        val mPackageManager: PackageManager = requireActivity().packageManager
        val resolveInfoList =
            mPackageManager.queryIntentActivities(mIntent, PackageManager.MATCH_DEFAULT_ONLY)

        if (resolveInfoList.isNotEmpty()) {
            startActivityForResult(
                Intent.createChooser(
                    Intent(Intent.ACTION_PICK, mUri).apply {
                        type = "image/*" // 指定类型为图片
                    },
                    "選取圖片"
                ),
                AppConfig.REQUEST_SELECT_VIDEO
            )
        } else {
            startActivityForResult(mIntent, AppConfig.REQUEST_SELECT_VIDEO)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConfig.REQUEST_SELECT_VIDEO) {
                // 处理选取视频的逻辑

                // 注意：在处理文件读取时，如果缺少 READ_EXTERNAL_STORAGE 权限，可能会抛出 IOException
                try {
                    val imageUri: Uri? = data?.data
                    val imageStream: InputStream? = activity?.contentResolver?.openInputStream(
                        imageUri!!
                    )
                    val selectedImage: Bitmap? = BitmapFactory.decodeStream(imageStream)

                    selectedImage?.let { storeImage(it) }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            } else if (requestCode == AppConfig.REQUEST_CAMERA) {
                val photo: Bitmap? = data?.getParcelableExtra("data")
                photo?.let { storeImage(it) }
            }
        }
    }


    private fun storeImage(image: Bitmap) {
        val width = image.width
        val height = image.height

        // 将图像等比例缩小至宽度为
        val MAX_WIDTH = 256

        var resize = 1f // 缩小值 resize 可为任意小数
        if (width > height) {
            // landscape
            resize = MAX_WIDTH.toFloat() / width
        } else {
            // portrait
            resize = MAX_WIDTH.toFloat() / height
        }

        val nWidth = (width * resize).toInt()
        val nHeight = (height * resize).toInt()

        tempImage = Bitmap.createScaledBitmap(image, nWidth, nHeight, true)

        // Log.e(TAG,"new Width = " + tempImage.getWidth());
        // Log.e(TAG,"new Height = " + tempImage.getHeight());

        binding.ivHead.setImageBitmap(tempImage)
        uploadImage()
    }

    private fun uploadImage() {
        val filesDir: File? = requireActivity().filesDir
        val cmdImageFile = File(filesDir, "image.jpg")

        try {
            val os: OutputStream = FileOutputStream(cmdImageFile)
            tempImage?.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "Error writing bitmap", e)
        }

        mainViewModel.putUpLoadImag(
            requireContext(),
            AppUtility.getLoginId(requireContext()).toString(),
            "0",
            AppUtility.getLoginPassword(requireContext()).toString(),
            AppUtility.getLoginId(requireContext()).toString(),
            cmdImageFile
        )

    }


}