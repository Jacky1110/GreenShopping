package com.jotangi.greenShop.member

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jotangi.greenShop.CoverPassword
import com.jotangi.greenShop.MainViewModel
import com.jotangi.greenShop.api.ApiConfig
import com.jotangi.greenShop.databinding.FragmentAccountLoginBinding
import com.jotangi.greenShop.model.LoginResponse
import com.jotangi.greenShop.utility.AppUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AccountLoginFragment : Fragment() {

    private val TAG: String = "(TAG)${javaClass.simpleName}"

    private lateinit var binding: FragmentAccountLoginBinding

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {

        initViewModel()
        initObserver()
        initAction()
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private fun initObserver() {

        mainViewModel.loginData.observe(viewLifecycleOwner) { result ->
            if (result != null) {

                loginSucceed(result)

            } else {

                Log.d(TAG, "initObserver: $result")
            }
        }
    }

    private fun loginSucceed(result: LoginResponse) {
        if (result.code == ApiConfig.API_CODE_SUCCESS) {


            AppUtility.updateLoginId(
                requireContext(),
                binding.loginIdEditText.text.toString()
            )

            AppUtility.updateLoginPassword(
                requireContext(),
                binding.loginPasswordEditText.text.toString()
            )

            binding.progressBar.visibility = View.GONE

            showPrivateDialog(
//                result.message,
                "登入成功",
                null
            )

        } else {

            binding.progressBar.visibility = View.GONE

            AppUtility.showPopDialog(
                requireContext(),
//                result.code,
                null,
                "帳號或密碼錯誤"
            )
        }
    }


    private fun initAction() {
        binding.apply {

            loginPasswordEditText.transformationMethod = CoverPassword()

            btnLogin.setOnClickListener {

                login()
            }

            btnRegister.setOnClickListener {

            }
        }
    }

    private fun login() {
        binding.apply {

            if (loginIdEditText.text.isNullOrEmpty()) {
                showPrivateDialog(
                    "帳號為必填！",
                    loginIdEditText
                )

                return
            }

            if (loginPasswordEditText.text.isNullOrEmpty()) {
                showPrivateDialog(
                    "密碼為必填！",
                    loginPasswordEditText
                )

                return
            }

            progressBar.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO) {
                val login = async {
                    mainViewModel.login(
                        requireContext(),
                        loginIdEditText.text.toString(),
                        loginPasswordEditText.text.toString(),
                    )
                }

                login.await()

            }
        }
    }

    private fun showPrivateDialog(
        message: String,
        curUI: Any?
    ) {
        val alert = AlertDialog.Builder(requireContext())
        val title = if (curUI == null) {
            "恭喜您"
        } else {
            "提醒！"
        }

        alert.setTitle(title)
        alert.setMessage(message)
        alert.setPositiveButton("確定") { _, _ ->

            when (curUI) {
                binding.loginIdEditText,
                binding.loginPasswordEditText -> {
                    return@setPositiveButton
                }

                else -> {
                    findNavController().popBackStack()
                }
            }
        }

        alert.show()
    }
}