package com.jotangi.greenShop.shop

import android.R
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jotangi.greenShop.BaseFragment
import com.jotangi.greenShop.databinding.AppBarMainBinding
import com.jotangi.greenShop.databinding.FragmentDynamicTabBinding
import com.jotangi.greenShop.model.ProductListResponse
import com.jotangi.greenShop.model.ProductType1VO
import com.jotangi.greenShop.model.ProductTypeResponse
import com.jotangi.greenShop.utility.AppUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.log

class DynamicTabFragment : BaseFragment(), ProductListClickListener {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentDynamicTabBinding


    private lateinit var fullCategoryData: MutableList<ProductTypeResponse>

    private var productType: String = ""
    private val ticketId: String = "api06"


    private val typeData = mutableListOf<ProductType1VO>()
    private val listData = mutableListOf<ProductListResponse>()

    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDynamicTabBinding.inflate(inflater, container, false)
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

    override fun onResume() {
        super.onResume()

        setBnv(1)

    }

    private fun initObserver() {

        mainViewModel.productTypeData.observe(viewLifecycleOwner) { result ->

            binding.pb2.visibility = View.VISIBLE

            if (result != null) {

                fullCategoryData = result as MutableList<ProductTypeResponse>

                if (typeData.isEmpty()) {

                    updateProductType()

                    binding.pb2.visibility = View.GONE

                } else {

                    binding.pb2.visibility = View.GONE

                    typeData.clear()

                    updateProductType()
                }
            }
        }

        mainViewModel.productListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {

                binding.pb2.visibility = View.VISIBLE

                updateProductList(result)

                binding.pb2.visibility = View.GONE
            }
        }

    }

    private fun updateProductList(result: List<ProductListResponse>) {

        productListAdapter.updateDataSource(result)
    }

    private fun updateProductType() {

        fullCategoryData.forEachIndexed { index, element ->

            val item = ProductType1VO(
                index.toString(),
                element.productType,
                element.productTypeName,
                isSelected = index == 0
            )

            typeData.add(item)

        }

        val spinnerOptions = typeData.map { it.productTypeName }
        val spinnerTitles = getPackageTitle(spinnerOptions)


        // 创建自定义适配器
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            spinnerTitles
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.productTypeSpinner.adapter = adapter

        Log.d(TAG, "spinnerOptions: $spinnerTitles")
    }

    private fun getPackageTitle(data: List<String>): MutableList<String> {
        val packageTitles = data.toMutableList()

        packageTitles.add("套票")

        return packageTitles
    }

    private fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val productTypeData = async {
                mainViewModel.getProductTypeData(
                    requireContext(),
                    AppUtility.getLoginId(requireContext())!!,
                    AppUtility.getLoginPassword(requireContext())!!
                )
            }

            productTypeData.await()
        }

    }

    private fun initView() {

        initRecyclerView()
    }


    private fun initRecyclerView() {

        binding.rT.apply {

            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )
            productListAdapter = ProductListAdapter(
                listData,
                requireContext(),
                this@DynamicTabFragment
            )
            this.adapter = productListAdapter
        }
    }

    private fun initAction() {
        binding.apply {

            // 設定預設選項
            val defaultPosition =
                AppUtility.getTypePosition(requireContext()) // 這裡的數字是你想要的預設選項的索引
            productTypeSpinner.setSelection(defaultPosition)

            productTypeSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {


                        productType = typeData[position].productType
                        AppUtility.updateTypePosition(requireContext(), position)

//                        if(){
//
//                        }

                        initProductList(productType)

                        Log.d(TAG, "position: ${typeData.size}")
                        Log.d(TAG, "id: ${typeData[position].productTypeName}")
                    }


                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Log.d(TAG, "onNothingSelected: $parent")
                    }

                }
        }
    }


    private fun initProductList(productType: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val productListData = async {
                mainViewModel.getProductListData(
                    requireContext(),
                    AppUtility.getLoginId(requireContext())!!,
                    AppUtility.getLoginPassword(requireContext())!!,
                    productType
                )
            }

            productListData.await()
        }
    }

    override fun onProductListItemClick(vo: ProductListResponse) {


    }

}