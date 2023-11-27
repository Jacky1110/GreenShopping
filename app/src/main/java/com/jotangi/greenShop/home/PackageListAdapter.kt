package com.jotangi.greenShop.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.greenShop.databinding.ItemPagerBinding
import com.jotangi.greenShop.model.PackageListResponse


interface PackageListClickListener {
    fun onPackageListItemClick(vo: PackageListResponse)
}

class PackageListAdapter(
    private var data: List<PackageListResponse>,
    val context: Context,
    private val listener: PackageListClickListener?
) : RecyclerView.Adapter<PackageListItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageListItemViewHolder {
        val binding = ItemPagerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PackageListItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PackageListItemViewHolder, position: Int) {
        holder.bind(
            data[position],
            listener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateDataSource(dataSource: List<PackageListResponse>) {
        this.data = dataSource

        this.notifyDataSetChanged()
    }


}
