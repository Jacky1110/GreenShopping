package com.jotangi.greenShop.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jotangi.greenShop.BaseFragment
import com.jotangi.greenShop.api.ApiConfig
import com.jotangi.greenShop.databinding.AppBarMainBinding
import com.jotangi.greenShop.databinding.FragmentHomeMainBinding
import com.jotangi.greenShop.model.BannerListVO
import com.jotangi.greenShop.model.PackageListResponse
import com.jotangi.greenShop.utility.AppUtility
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HomeMainFragment : BaseFragment(), PackageListClickListener {


    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentHomeMainBinding

    private var data = mutableListOf<PackageListResponse>()
    private lateinit var packageListAdapter: PackageListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMainBinding.inflate(inflater, container, false)
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

        setBnv(0)
    }

    private fun initObserver() {

        mainViewModel.bannerData.observe(viewLifecycleOwner) { result ->
            if (result != null) {

                updateBanner(result)

            }
        }

        mainViewModel.packageListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {

                updateListView(result)

            }
        }

    }

    private fun updateListView(result: List<PackageListResponse>) {

        packageListAdapter.updateDataSource(result)

    }

    private fun updateBanner(result: List<BannerListVO>) {

        binding.apply {

            mainBanner.setAdapter(object : BannerImageAdapter<BannerListVO>(result) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerListVO?,
                    position: Int,
                    size: Int
                ) {
                    if (holder != null) {
                        Glide.with(holder.itemView)
                            .load(ApiConfig.URL_HOST + "ticketec/" + data?.bannerPicture)
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                            .into(holder.imageView)

                        holder.imageView.setOnClickListener {
                            if (!data?.bannerLink.isNullOrEmpty()) {
                                openBannerLink(data!!.bannerLink)
                            }
                        }

                    }
                }
            })

                .addBannerLifecycleObserver(this@HomeMainFragment) //添加生命周期观察者
                .setIndicator(CircleIndicator(requireContext()))
        }
    }

    private fun openBannerLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)

        intent.data = Uri.parse(url)
        startActivity(intent)
    }


    private fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val bannerDeferred = async {
                mainViewModel.getMainBannerData(
                    requireContext(),
                    AppUtility.getLoginId(requireContext())!!,
                    AppUtility.getLoginPassword(requireContext())!!
                )
            }

            val packageListData = async {
                mainViewModel.getPackageListData(
                    requireContext(),
                    AppUtility.getLoginId(requireContext())!!,
                    AppUtility.getLoginPassword(requireContext())!!
                )
            }

            bannerDeferred.await()
            packageListData.await()
        }
    }

    private fun initView() {

        initRecyclerView()
    }

    private fun initRecyclerView() {

        binding.homeRecyclerview.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )
            packageListAdapter = PackageListAdapter(
                data,
                requireContext(),
                this@HomeMainFragment
            )
            this.adapter = packageListAdapter
        }
    }


    private fun initAction() {
        binding.apply {

            vOldCar.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                val url =
                    Uri.parse("https://www.facebook.com/rilinkiscooter/shop/?referral_code=page_shop_tab&preview=1")
                intent.data = url
                startActivity(intent)
            }


        }
    }

    override fun onPackageListItemClick(vo: PackageListResponse) {

    }


}