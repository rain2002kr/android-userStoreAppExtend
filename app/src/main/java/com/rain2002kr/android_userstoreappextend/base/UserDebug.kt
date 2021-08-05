package com.rain2002kr.android_usedstoreappextend.base

enum class UserDebug(val TAG : String, val success:String, val faild:String) {
	BASE_ACT("BaseActivity","",""),
	MAIN_ACT("MainActivity", "전송 완료", "전송 실패"),
	ADD_SELL_ACT("AddSellProductActivity", "전송 완료", "전송 실패")

}