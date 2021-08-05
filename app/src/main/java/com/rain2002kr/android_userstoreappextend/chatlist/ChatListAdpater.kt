package com.rain2002kr.android_userstoreappextend.chatlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rain2002kr.android_userstoreappextend.databinding.ItemChatListBinding

class ChatListAdapter(val onClickListener:(ChatListItem)->Unit): ListAdapter<ChatListItem,ChatListAdapter.ViewHolder>(diffUtil) {
	inner class ViewHolder(private val binding:ItemChatListBinding) : RecyclerView.ViewHolder(binding.root){
		fun bind(chatListItem: ChatListItem){
			 chatListItem.buyerId

			binding.root.setOnClickListener {
				onClickListener(chatListItem)
			}
			binding.chatRoomTitleTextView.text = chatListItem.itemTitle
			binding.itemPriceTextView.text = chatListItem.itemPrice
			binding.nickNameTitleTextView.text = chatListItem.sellerNickName
			binding.lastMessageTextView.text = chatListItem.lastMessage

			Glide
				.with(binding.itemImageView.context)
				.load(chatListItem.itemImageUri)
				.into(binding.itemImageView)


		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListAdapter.ViewHolder {
		return ViewHolder(ItemChatListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
	}

	override fun onBindViewHolder(holder: ChatListAdapter.ViewHolder, position: Int) {
		holder.bind(currentList[position])
	}
	companion object {
		val diffUtil =object : DiffUtil.ItemCallback<ChatListItem>(){
			override fun areItemsTheSame(oldItem: ChatListItem, newItem: ChatListItem): Boolean {
				return  oldItem.key == newItem.key
			}

			override fun areContentsTheSame(oldItem: ChatListItem, newItem: ChatListItem): Boolean {
				return  oldItem == newItem
			}
		}
	}
}