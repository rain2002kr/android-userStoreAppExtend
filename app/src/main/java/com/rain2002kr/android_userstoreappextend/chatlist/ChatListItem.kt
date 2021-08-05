package com.rain2002kr.android_userstoreappextend.chatlist

data class ChatListItem(
	val buyerId : String,
	val sellerId : String,
	val sellerNickName : String,
	val itemTitle : String,
	val itemPrice : String,
	val itemImageUri : String,
	val lastMessage :String,
	val key : Long,
){
	constructor():this("","","","","","","",0 )
}
