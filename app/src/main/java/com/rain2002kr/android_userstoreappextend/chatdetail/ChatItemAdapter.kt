package com.rain2002kr.android_userstoreappextend.chatdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rain2002kr.android_userstoreappextend.databinding.ItemChatBinding
import com.rain2002kr.android_userstoreappextend.databinding.ItemChatListBinding

class ChatItemAdapter(val onClickListener:(chatItem:ChatItem)->Unit):ListAdapter<ChatItem,ChatItemAdapter.ViewHolder>(diffUtil){
	inner class ViewHolder(private val binding: ItemChatBinding):RecyclerView.ViewHolder(binding.root){
		fun bind(chatItem: ChatItem){
			binding.senderTextView.text = chatItem.senderNickName
			binding.messageTextView.text = chatItem.message


			binding.root.setOnClickListener {
				onClickListener(chatItem)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemAdapter.ViewHolder {
		return ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
	}

	override fun onBindViewHolder(holder: ChatItemAdapter.ViewHolder, position: Int) {
		holder.bind(currentList[position])
	}
	companion object{
		val diffUtil= object :DiffUtil.ItemCallback<ChatItem>(){
			override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
				return oldItem == newItem
			}

			override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
				return oldItem == newItem
			}
		}
	}
}