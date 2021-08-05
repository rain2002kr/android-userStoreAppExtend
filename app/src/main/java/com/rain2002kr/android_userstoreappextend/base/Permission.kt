package com.rain2002kr.android_usedstoreappextend.base

enum class Permission(
	val TITLE:String, val MESSAGE:String, val REQUEST_CODE:Int
	,val GRANTED:String, val DENIED:String


	)

{
	READ_EXT_MEMORY("외장메모리 권한", "외장메모리 권한이 필요합니다.",1000,
		"외장 메모리 권한을 허용 하셨습니다.","외장 메모리 권한을 허용 하지 않으셨습니다."),


	GET_CONTENT("인텐트사진 권한", "",1001,
	"사진을 가져 왔습니다.", "사진을 가져오지 못했습니다."),

//	CAMERA("카메라권한", "카메라권한이 필요합니다.",1002,
//		"카메라 권한을 허용 하셨습니다.","카메라 권한을 허용 하지 않으셨습니다."),

}