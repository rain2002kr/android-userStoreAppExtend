package com.rain2002kr.android_userstoreappextend

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.database.DatabaseErrorHandler
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.rain2002kr.android_usedstoreappextend.base.BaseActivity
import com.rain2002kr.android_usedstoreappextend.base.Permission
import com.rain2002kr.android_usedstoreappextend.base.UserDebug
import com.rain2002kr.android_usedstoreappextend.home.SellProduct
import com.rain2002kr.android_userstoreappextend.DBKey.Companion.DB_SELL_PRODUCT
import com.rain2002kr.android_userstoreappextend.DBKey.Companion.FILE_SERVER_PHOTO
import com.rain2002kr.android_userstoreappextend.base.BaseFunction
import com.rain2002kr.android_userstoreappextend.databinding.ActivityAddProductSellBinding
import java.math.BigDecimal
import java.text.DecimalFormat

class AddSellProductActivity : BaseActivity() {
//todo 파이어베이스 db 에 올리기, uri 추가하기, 완료되면 home 으로 보내기
	//todo 파이어베이스 파일 db, 1. 올리기(기기의 content Uri), 2. down_load_url 가져오기
	//todo 리얼타임 db download_url 올리기
	//todo 파일올라가는동안 프로그래스바 처리하기


	private val auth by lazy { Firebase.auth }

	private val binding: ActivityAddProductSellBinding by lazy {ActivityAddProductSellBinding.inflate(layoutInflater)}
	private lateinit var dbRef: DatabaseReference
	private lateinit var seletedUri: Uri
	private val storageFirebase: FirebaseStorage by lazy { Firebase.storage }

	private val listener = object : ChildEventListener {
		override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {}
		override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
		override fun onChildRemoved(snapshot: DataSnapshot) {}
		override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
		override fun onCancelled(error: DatabaseError) {}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		initGetSellProductInfoFromDatabase()

		initAddSellProductItem()
		initAddImageFile()


	}

	private fun initGetSellProductInfoFromDatabase() {
		dbRef = Firebase.database.reference.child(DB_SELL_PRODUCT)

	}

	private fun initAddSellProductItem() {
		binding.addSellProductDbButton.setOnClickListener {
			when {
				// todo title & price empty check
				enableSendProductItemToServer() -> {
					// progressBar show
					progressBarShow()
					// todo upload photo firebase file server
					uploadPhotoToFileServer(seletedUri, successHandler = { uri ->
						Log.d(UserDebug.ADD_SELL_ACT.TAG, "uri $it")
						// todo upload sell product data to real time database file server with download URI
						auth.currentUser?.let{ user ->
							val	sellProduct = SellProduct(user.uid, user.displayName.toString(), getItemTitle(), getItemPrice(), uri, System.currentTimeMillis())
							uploadSellProductItemToDataServer(sellProduct, successHandler = {
								Log.d(
									UserDebug.ADD_SELL_ACT.TAG,
									"uploadSellProductItemToDataServer successHandler"
								)
								finish()
								progressBarHide()
							}, errorHandler = {
								Log.d(
									UserDebug.ADD_SELL_ACT.TAG,
									"uploadSellProductItemToDataServer errorHanlder,$it"
								)
								progressBarHide()
							})
						}
					}, errorHandler = {
						Log.d(UserDebug.ADD_SELL_ACT.TAG, "uri error hanlder,$it")
						progressBarHide()
					})
				}
			}
		}

	}

	private fun progressBarShow(){
		binding.LoadImageProgressBar.isVisible = true
	}
	private fun progressBarHide(){
		binding.LoadImageProgressBar.isVisible = false
	}

	private fun uploadSellProductItemToDataServer(
		sellProduct: SellProduct,
		successHandler: (String) -> Unit,
		errorHandler: () -> Unit
	) {
		//todo UPLOAD productItem to RealTime DB
		dbRef.push().setValue(sellProduct)
			.addOnSuccessListener {
				successHandler(UserDebug.ADD_SELL_ACT.success)
			}
			.addOnFailureListener {
				errorHandler()
			}
	}

	private fun initAddImageFile() {
		binding.imageAddButton.setOnClickListener {
			requirePermission()
		}

	}

	private fun uploadPhotoToFileServer(
		uri: Uri,
		successHandler: (String) -> Unit,
		errorHandler: (String) -> Unit
	) {
		if (uri != null) {
			upLoadPhotoToFirebase(uri, completedServer = {
				successHandler(it)
			}, errorCompleted = {
				errorHandler(it)
			})
		}


	}

	private fun upLoadPhotoToFirebase(
		uri: Uri,
		completedServer: (String) -> Unit,
		errorCompleted: (String) -> Unit
	) {
		val fileName = "${System.currentTimeMillis()}.png"
		storageFirebase.reference.child(FILE_SERVER_PHOTO).child(fileName).putFile(uri)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					storageFirebase.reference.child(FILE_SERVER_PHOTO).child(fileName).downloadUrl
						.addOnSuccessListener {
							completedServer(it.toString())
						}.addOnFailureListener {
							errorCompleted("실패")
						}
				}
			}
			.addOnFailureListener {
				errorCompleted("실패")
			}

	}

	private fun enableSendProductItemToServer() =
		binding.titleEditText.text.isNotEmpty() && binding.priceEditText.text.isNotEmpty()

	private fun getItemTitle() = binding.titleEditText.text.toString()
	private fun getItemPrice():String{
		val price = binding.priceEditText.text.toString()
		val dec = DecimalFormat("#,###")
		return dec.format(price.toBigDecimal()).toString() + " 원"
	}
	private fun getItemImageUri() = binding.imageView.toString()

	private fun isLoginCheck() = auth.currentUser != null

	private fun requirePermission() {
		requirePermissions(
			arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
			Permission.READ_EXT_MEMORY.REQUEST_CODE
		)
		Log.d(UserDebug.MAIN_ACT.TAG, Permission.READ_EXT_MEMORY.MESSAGE)
	}

	private fun startContentProvider() {
		val intent = Intent(Intent.ACTION_GET_CONTENT)
		intent.type = "image/*"
		startActivityForResult(intent, Permission.GET_CONTENT.REQUEST_CODE)
		//registerForActivityResult
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (resultCode != Activity.RESULT_OK) return
		when (requestCode) {
			Permission.GET_CONTENT.REQUEST_CODE -> {
				val uri = data?.data
				if (uri !== null) {
					binding.imageView.setImageURI(uri)
					seletedUri = uri
				} else {
					Toast.makeText(this, "이미지를 가져오지 못했습니다.", Toast.LENGTH_LONG).show()
				}
			}
			else -> {
				Toast.makeText(this, Permission.GET_CONTENT.DENIED, Toast.LENGTH_LONG).show()
			}
		}

	}

	override fun permissionRationale(permissions: Array<String>, requestCode: Int) {
		when (requestCode) {
			Permission.READ_EXT_MEMORY.REQUEST_CODE -> {
				Permission.READ_EXT_MEMORY.let {
					showPermissionContextPopup(permissions, it.TITLE, it.MESSAGE, requestCode)
				}
			}

		}
	}

	override fun permissionGranted(requestCode: Int) {
		when (requestCode) {
			Permission.READ_EXT_MEMORY.REQUEST_CODE -> {
				Toast.makeText(this, Permission.READ_EXT_MEMORY.GRANTED, Toast.LENGTH_LONG).show()
				startContentProvider()
			}
		}
	}

	override fun permissionDenied(requestCode: Int) {
		when (requestCode) {
			Permission.READ_EXT_MEMORY.REQUEST_CODE -> {
				Toast.makeText(this, Permission.READ_EXT_MEMORY.DENIED, Toast.LENGTH_LONG).show()
				//startContentProvider()
			}
		}
	}
}