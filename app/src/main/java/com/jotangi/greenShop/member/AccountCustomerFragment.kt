package com.jotangi.greenShop.member

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jotangi.greenShop.BaseFragment
import com.jotangi.greenShop.R
import com.jotangi.greenShop.databinding.AppBarMainBinding
import com.jotangi.greenShop.databinding.FragmentAccountCustomerBinding
import com.jotangi.greenShop.databinding.FragmentMemberBinding


class AccountCustomerFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentAccountCustomerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }



}