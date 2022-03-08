package com.example.testefishery.ui.listprice

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testefishery.data.models.Price
import com.example.testefishery.databinding.ItemPriceBinding

class ListPriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val itemPriceBinding: ItemPriceBinding = ItemPriceBinding.bind(itemView)

    fun onBind(price: Price) {
        itemPriceBinding.apply {
            txtTimeParsed.text = price.tgl_parsed
            txtComodity.text = price.komoditas
            txtArea.text = "${price.area_kota}, ${price.area_provinsi}"
            txtPrice.text = price.price
            txtSize.text = "size : ${price.size}"
        }
    }
}