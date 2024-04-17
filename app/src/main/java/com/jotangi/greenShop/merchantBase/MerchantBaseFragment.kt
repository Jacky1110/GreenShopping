package com.jotangi.greenShop.merchantBase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.jotangi.greenShop.BaseFragment
import com.jotangi.greenShop.R
import com.jotangi.greenShop.databinding.AppBarMainBinding
import com.jotangi.greenShop.databinding.FragmentDynamicTabBinding
import com.jotangi.greenShop.databinding.FragmentMerchantBaseBinding
import com.jotangi.greenShop.model.ProductTypeResponse
import com.jotangi.greenShop.model.StoreTypeResponse
import com.jotangi.greenShop.utility.AppUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MerchantBaseFragment : BaseFragment() {


    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentMerchantBaseBinding

    private lateinit var fullCategoryData: MutableList<StoreTypeResponse>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMerchantBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        initObserver()
        initData()
        initView()
        initAction()
    }

    private fun initObserver() {

        mainViewModel.storeTypeListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {

            }

        }
    }

    private fun initData() {

        lifecycleScope.launch(Dispatchers.IO) {
            val storeTypeData = async {
                mainViewModel.getStoreTypeData(
                    requireContext(), AppUtility.getLoginId(requireContext())!!,
                    AppUtility.getLoginPassword(requireContext())!!,
                )
            }

            storeTypeData.await()
        }

    }

    private fun initView() {

    }

    private fun initAction() {

    }


}