package com.jotangi.greenShop.home

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jotangi.greenShop.api.ApiConfig
import com.jotangi.greenShop.databinding.ItemPagerBinding
import com.jotangi.greenShop.model.PackageListResponse

class PackageListItemViewHolder(val binding: ItemPagerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: PackageListResponse,
        listener: PackageListClickListener?
    ) {

        binding.apply {

//            itemTimeSaleConstraintLayout.setOnClickListener {
//                listener?.onTimeSaleItemClick(data)
//            }

            productTitleTextView.text = data.productName
            productPriceTextView.text = "NT$ " + data.productPrice

            Glide.with(root).load(ApiConfig.URL_HOST + "ticketec/" + data.productPicture)
                .into(ivPag)
        }

    }
}