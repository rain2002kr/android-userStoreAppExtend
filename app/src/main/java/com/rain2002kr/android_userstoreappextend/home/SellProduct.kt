package com.rain2002kr.android_usedstoreappextend.home

data class SellProduct(
    val sellerId : String,
    val sellerNickName : String,
    val title : String,
    val price : String,
    val imageUri : String,
    val createAt : Long
){
    // todo 파이어베이스 데이터 불러올때, 빈 생성자가 있어야 한다.
    constructor():this("","", "", "","",0)
}
