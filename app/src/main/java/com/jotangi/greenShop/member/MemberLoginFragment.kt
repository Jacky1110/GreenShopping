package com.jotangi.greenShop.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.jotangi.greenShop.BaseFragment
import com.jotangi.greenShop.R
import com.jotangi.greenShop.databinding.AppBarMainBinding
import com.jotangi.greenShop.databinding.FragmentMemberLoginBinding
import com.jotangi.greenShop.storeManager.StoreManagerFragment

class MemberLoginFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentMemberLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onStart() {
        super.onStart()

        fragmentChange(0)

    }

    private fun init() {

        setupLoginTitle()
        initObserver()
        initAction()
        initView()
    }


    private fun initAction() {

        binding.apply {
            tabLayout.addTab(tabLayout.newTab().setText("會員登入"))
            tabLayout.addTab(tabLayout.newTab().setText("店長登入"))
            tabLayout.tabMode = TabLayout.MODE_FIXED
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {

                    fragmentChange(tab.position)

                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

        }
    }

    private fun fragmentChange(position: Int) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        if (0 == position) {
            transaction.replace(R.id.framelayout, AccountLoginFragment())
        } else {
            transaction.replace(R.id.framelayout, StoreManagerFragment())
        }
        transaction.commit()
    }

    private fun initObserver() {

    }


    private fun initView() {
        val navView = mActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.visibility = View.GONE
    }
}