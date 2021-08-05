package com.rain2002kr.android_userstoreappextend.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rain2002kr.android_usedstoreappextend.home.SellProduct
import com.rain2002kr.android_userstoreappextend.databinding.FragmentHomeBinding
import com.rain2002kr.android_userstoreappextend.databinding.ItemSellProductBinding
import java.text.SimpleDateFormat
import java.util.*

class SellProductAdapter(private val itemClickListener: (SellProduct) -> Unit) :
	ListAdapter<SellProduct, SellProductAdapter.ViewHolder>(diffUtil) {
	inner class ViewHolder(private val binding: ItemSellProductBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(sellProduct: SellProduct) {
			val format = SimpleDateFormat("MM월 dd일")
			val date = Date(sellProduct.createAt)

			binding.titleTextView.text = sellProduct.title
			binding.priceTextView.text = sellProduct.price
			binding.dateTextView.text = format.format(date).toString()

			Glide
				.with(binding.thumbnailImageView.context)
				.load(sellProduct.imageUri)
				.into(binding.thumbnailImageView)

			binding.root.setOnClickListener {
				itemClickListener(sellProduct)
			}

		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellProductAdapter.ViewHolder {
		return ViewHolder(ItemSellProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}

	override fun onBindViewHolder(holder: SellProductAdapter.ViewHolder, position: Int) {
		holder.bind(currentList[position])
	}

	companion object {
		val diffUtil = object :DiffUtil.ItemCallback<SellProduct>() {
			override fun areItemsTheSame(oldItem: SellProduct, newItem: SellProduct): Boolean {
				return oldItem.createAt == newItem.createAt
			}

			override fun areContentsTheSame(oldItem: SellProduct, newItem: SellProduct): Boolean {
				return oldItem == newItem
			}
		}
	}
}