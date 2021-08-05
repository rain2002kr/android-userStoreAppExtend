package com.rain2002kr.android_userstoreappextend.base

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

open class BaseFunction :AppCompatActivity() {
	// TODO 유저 로그 펑션
	fun userLog(tag:String, message:String){
		Log.d(tag,message)
	}
	// TODO 유저 토스트 롱 펑션
	fun userToastLong(context: Context, message:String){
		Toast.makeText(context,message, Toast.LENGTH_LONG).show()
	}
	// TODO 유저 토스트 숏 펑션
	fun userToastShort(context: Context, message:String){
		Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
	}
	// todo 추후 스낵바 공부 할때 사용해볼것
	fun userSnackBarLong(view: View, message:String){
		Snackbar.make(view, message,Snackbar.LENGTH_LONG).show()
	}
//	fun userSnackBarShort(view: View, message:String){
//		Snackbar.make(view, message,Snackbar.LENGTH_SHORT).show()
//	}

}