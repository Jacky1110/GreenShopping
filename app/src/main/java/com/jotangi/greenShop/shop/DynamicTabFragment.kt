package com.jotangi.greenShop.shop

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jotangi.greenShop.BaseFragment
import com.jotangi.greenShop.api.AppConfig
import com.jotangi.greenShop.databinding.AppBarMainBinding
import com.jotangi.greenShop.databinding.FragmentDynamicTabBinding
import com.jotangi.greenShop.home.PackageListAdapter
import com.jotangi.greenShop.home.PackageListClickListener
import com.jotangi.greenShop.model.PackageListResponse
import com.jotangi.greenShop.model.ProductListResponse
import com.jotangi.greenShop.model.ProductType1VO
import com.jotangi.greenShop.model.ProductTypeResponse
import com.jotangi.greenShop.utility.AppUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DynamicTabFragment : BaseFragment(), ProductListClickListener, PackageListClickListener {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentDynamicTabBinding


    private lateinit var fullCategoryData: MutableList<ProductTypeResponse>

    private var productType: String = ""


    private val typeData = mutableListOf<ProductType1VO>()
    private val listData = mutableListOf<ProductListResponse>()
    private val packageListData = mutableListOf<PackageListResponse>()

    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var packageListAdapter: PackageListAdapter

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


        mainViewModel.maintenanceListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.pb2.visibility = View.VISIBLE

                listData.clear()

                updateProductList(result)

                binding.pb2.visibility = View.GONE

            }

        }

        mainViewModel.carRentalListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.pb2.visibility = View.VISIBLE

                listData.clear()

                updateProductList(result)

                binding.pb2.visibility = View.GONE

            }

        }

        mainViewModel.accessoriesListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.pb2.visibility = View.VISIBLE

                listData.clear()

                updateProductList(result)

                binding.pb2.visibility = View.GONE

            }

        }

        mainViewModel.secondhandListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.pb2.visibility = View.VISIBLE

                listData.clear()

                updateProductList(result)

                binding.pb2.visibility = View.GONE

            }

        }

        mainViewModel.packageListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.pb2.visibility = View.VISIBLE

                listData.clear()

                updatePackageList(result)

                binding.pb2.visibility = View.GONE

            }
        }


    }

    private fun updateProductList(result: List<ProductListResponse>) {

        binding.apply {


            productListAdapter.updateDataSource(result)
        }
    }

    private fun updatePackageList(result: List<PackageListResponse>) {

        binding.apply {

            packageListAdapter.updateDataSource(result)

        }

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

        val newItem = ProductType1VO(
            fullCategoryData.size.toString(), // 新项的索引为列表大小
            AppConfig.TICKET_ID, // 新的 product_type
            AppConfig.TICKET_NAME, // 新的 producttype_name
            isSelected = false // 默认不选中
        )

        typeData.add(newItem)

        Log.d(TAG, "typeData: $typeData")

        val spinnerOptions = typeData.map { it.productTypeName }


        // 创建自定义适配器
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            spinnerOptions
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.productTypeSpinner.adapter = adapter

        Log.d(TAG, "spinnerOptions: $spinnerOptions")
    }

    private fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {

            val productListData = async {
                mainViewModel.getProductListData(
                    requireContext(),
                    AppUtility.getLoginId(requireContext())!!,
                    AppUtility.getLoginPassword(requireContext())!!,
                    AppConfig.PRODUCT_TYPE
                )
            }

            productListData.await()

            val productTypeData = async {
                delay(800)
                mainViewModel.getProductTypeData(
                    requireContext(),
                    AppUtility.getLoginId(requireContext())!!,
                    AppUtility.getLoginPassword(requireContext())!!
                )
            }

            productTypeData.await()


            val packageListData = async {
                mainViewModel.getPackageListData(
                    requireContext(),
                    AppUtility.getLoginId(requireContext())!!,
                    AppUtility.getLoginPassword(requireContext())!!
                )
            }



            packageListData.await()
        }

    }

    private fun initView() {

        initRecyclerView()
        initPackageListRecyclerView()
    }


    private fun initRecyclerView() {

        binding.rTProduct.apply {

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

    private fun initPackageListRecyclerView() {

        binding.rTPackage.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )
            packageListAdapter = PackageListAdapter(
                packageListData,
                requireContext(),
                this@DynamicTabFragment
            )
            this.adapter = packageListAdapter
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


                        if (position >= 0 && position < typeData.size) {

                            productType = typeData[position].productType
                            AppUtility.updateTypePosition(requireContext(), position)

                            when (productType) {

                                "maintenance" -> {
                                    mainViewModel.getMaintenanceList()
                                    initRecyclerViewVisibility(productType)
                                }

                                "Rent" -> {
                                    mainViewModel.getCarRentalList()
                                    initRecyclerViewVisibility(productType)

                                }

                                "SC001" -> {
                                    mainViewModel.getAccessoriesList()
                                    initRecyclerViewVisibility(productType)

                                }

                                "Used Car" -> {

                                    mainViewModel.getSecondhandList()
                                    initRecyclerViewVisibility(productType)

                                }

                                "api06" -> {

                                    mainViewModel.getPackageList()
                                    initRecyclerViewVisibility(productType)

                                }
                            }

                        }


                        Log.d(TAG, "position.size: ${typeData.size}")
                        Log.d(TAG, "productTypeName: ${typeData[position].productType}")
                    }


                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Log.d(TAG, "onNothingSelected: $parent")
                    }

                }
        }
    }

    private fun initRecyclerViewVisibility(productType: String) {
        binding.apply {

            if (productType != AppConfig.TICKET_ID) {

                rTPackage.visibility = View.GONE
                rTProduct.visibility = View.VISIBLE

            } else {

                rTPackage.visibility = View.VISIBLE
                rTProduct.visibility = View.GONE
            }
        }
    }

    override fun onProductListItemClick(vo: ProductListResponse) {


    }

    override fun onPackageListItemClick(vo: PackageListResponse) {

    }

}