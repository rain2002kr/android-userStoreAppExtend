package com.rain2002kr.android_userstoreappextend.chatdetail

data class ChatItem(
	val senderId : String,
	val senderNickName : String,
	val message : String,

){
	constructor():this("","","")
}
