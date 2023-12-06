package com.jotangi.greenShop.shop

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jotangi.greenShop.api.ApiConfig
import com.jotangi.greenShop.databinding.ItemShopStoreListBinding
import com.jotangi.greenShop.model.ProductListResponse

class ProductListViewItemHolder(val binding: ItemShopStoreListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: ProductListResponse,
        listener: ProductListClickListener?
    ) {

        binding.apply {

            conShopList.setOnClickListener {
                listener?.onProductListItemClick(data)
            }

            tvName.text = data.productName
            tvMoney.text = "NT$ " + data.productPrice

            Glide.with(root).load(ApiConfig.URL_HOST + "ticketec/" + data.productPicture)
                .into(imgPackage)
        }

    }

}