package com.jotangi.greenShop.shop

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.greenShop.databinding.ItemPagerBinding
import com.jotangi.greenShop.databinding.ItemShopStoreListBinding
import com.jotangi.greenShop.home.PackageListClickListener
import com.jotangi.greenShop.home.PackageListItemViewHolder
import com.jotangi.greenShop.model.PackageListResponse
import com.jotangi.greenShop.model.ProductListResponse


interface ProductListClickListener {
    fun onProductListItemClick(vo: ProductListResponse)
}


class ProductListAdapter(
    private var data: List<ProductListResponse>,
    val context: Context,
    private val listener: ProductListClickListener?
) : RecyclerView.Adapter<ProductListViewItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewItemHolder {
        val binding = ItemShopStoreListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductListViewItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewItemHolder, position: Int) {
        holder.bind(
            data[position],
            listener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateDataSource(dataSource: List<ProductListResponse>) {
        this.data = dataSource

        this.notifyDataSetChanged()
    }


}