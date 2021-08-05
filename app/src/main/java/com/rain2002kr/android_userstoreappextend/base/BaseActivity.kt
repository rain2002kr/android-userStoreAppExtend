package com.rain2002kr.android_usedstoreappextend.base

import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){

	// TODO 유저 권한 설명 해줄때 띄워주는 인터페이스
	abstract fun permissionRationale(permissions: Array<String>, requestCode: Int)
	// TODO 유저 권한 허용 되었을때 띄워주는 인터페이스
	abstract fun permissionGranted(requestCode:Int)
	// TODO 유저 권한 거부 되었을때 띄워주는 인터페이스
	abstract fun permissionDenied(requestCode:Int)

	fun requirePermissions(permissions : Array<String>, requestCode: Int) {
		// TODO ContextCompat.checkSelfPermission 메소드는 권한이 있는 경우 true, 없는경우 false 를 반환
		val isAllPermissionGranted =
			permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }

		if (isAllPermissionGranted) {
			permissionGranted(requestCode)
		} else {
			// TODO 이 메서드는 유저에게 권한을 요청할때, 그 권한이 필요한 이유를 유저에게 설명 해주려고 할때 사용하는 메서드 입니다.
			permissionRationale(permissions, requestCode)
		}
	}

	// TODO 권한요청을 하는 유저 팝업 메서드
	// TODO 교육용 팝업 확인 후 권한 팝업을 띄우는 기능
	fun showPermissionContextPopup(permissions : Array<String>, message:String, title:String, requestCode: Int) {
		AlertDialog.Builder(this).setTitle(title)
			.setMessage(message)
			.setPositiveButton("동의하기") { _, _ ->
				requestPermissions(
					permissions,
					requestCode
				)
			}
			.setNegativeButton("취소하기", { _, _ -> })
			.create()
			.show()
	}

	// TODO 권한 요청에 대한 결과 처리
	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
			permissionGranted(requestCode)
		} else {
			permissionDenied(requestCode)
		}
	}

	companion object {
		const val TAG = "BaseActivity"
	}


}