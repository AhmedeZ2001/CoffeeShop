package com.example.coffeshop.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeshop.R
import com.example.coffeshop.domain.model.Coffee

class Adapter(private var product_List: MutableList<Coffee>, var itemClick: ItemClick) :
    RecyclerView.Adapter<Adapter.Holder>() {

    class Holder(val view: View, private val Listener: ItemClick) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val itemName: TextView = view.findViewById(R.id.tv_product_name)
        val itemPrice: TextView = view.findViewById(R.id.tv_product_price)
        val itemImage: ImageView = view.findViewById(R.id.iv_photo)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.Listener.onItemClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.product_view, parent, false)
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val product = product_List[position]
        holder.itemName.text = product.name
        holder.itemPrice.text = product.price.toString()
        Glide.with(holder.itemImage).load(product.image).into(holder.itemImage)

    }

    override fun getItemCount(): Int {
        return product_List.size
    }

    interface ItemClick {
        fun onItemClick(position: Int)
    }
}