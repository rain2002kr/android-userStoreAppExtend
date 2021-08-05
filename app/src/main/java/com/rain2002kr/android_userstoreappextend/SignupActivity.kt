package com.rain2002kr.android_userstoreappextend

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.rain2002kr.android_userstoreappextend.databinding.ActivitySignupBinding


class SignupActivity : AppCompatActivity() {
	private val binding: ActivitySignupBinding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
	private val auth: FirebaseAuth by lazy { Firebase.auth}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		enableButtonClicked()
		inputEmailAndPasswordCheck()
		initSignUpToServer()

	}

	private fun initSignUpToServer() {
		binding.signUpButton.setOnClickListener {
			if (!inputIsNotEmptyCheck())
				return@setOnClickListener

			val email: String = binding.emailEditText.text.toString()
			val nickName: String = binding.nickNameEditText.text.toString()
			val password: String = binding.passwordEditText.text.toString()




			auth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener {
					Log.d(TAG, "회원가입 정보를 전송 했습니다.")
					profileUpdateFunction(nickName)
				}
				.addOnSuccessListener {
					Log.d(TAG, "회원가입에 성공 했습니다.")
					finish()
				}
				.addOnFailureListener { Log.d(TAG, "회원가입에 실패 했습니다.") }
		}

	}

	private fun profileUpdateFunction(nickName: String) {
		val profileUpdates = userProfileChangeRequest {
			displayName = nickName
			photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
		}

		auth.currentUser?.let {
			it.updateProfile(profileUpdates)
				.addOnCompleteListener { task ->
					if (task.isSuccessful) {
						Log.d(TAG, "유저 정보가 업데이트되었습니다.")
					}
				}
		}
	}

	private fun inputIsNotEmptyCheck() =
		binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
				&& binding.nickNameEditText.text.isNotEmpty()

	private fun inputEmailAndPasswordCheck() {
		binding.emailEditText.addTextChangedListener {
			enableButtonClicked()
		}
		binding.passwordEditText.addTextChangedListener {
			enableButtonClicked()
		}
		binding.nickNameEditText.addTextChangedListener {
			enableButtonClicked()
		}
	}
	private fun enableButtonClicked() {
		if (!inputIsNotEmptyCheck()) {
			binding.signUpButton.isEnabled = false
			Log.d(TAG, "입력이 비어있습니다.${inputIsNotEmptyCheck()}")
			return
		} else {
			binding.signUpButton.isEnabled = true
			Log.d(TAG, "입력이 비어있지 않습니다.${inputIsNotEmptyCheck()}")
		}

	}


	companion object{
		const val TAG = "SignupActivity"
	}



}