package com.rain2002kr.android_usedstoreappextend.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rain2002kr.android_userstoreappextend.MainActivity
import com.rain2002kr.android_userstoreappextend.R
import com.rain2002kr.android_userstoreappextend.SignupActivity
import com.rain2002kr.android_userstoreappextend.databinding.FragmentMypageBinding

class MyPageFragement : Fragment(R.layout.fragment_mypage) {
	lateinit var binding: FragmentMypageBinding

	private lateinit var auth: FirebaseAuth
	private var loginStatus = false


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentMypageBinding.bind(view)
		auth = Firebase.auth
		enableButtonClicked()
		initLoginCheckFromServer()
		inputEmailAndPasswordCheck()
		//initSignUpToServer()
		initLogInToServer()
		initLogOutToServer()
		initMoveToSignupActvity()

	}

	private fun initMoveToSignupActvity() {
		binding.signUpButton.setOnClickListener {
			val intent = Intent(requireContext(),SignupActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onStart() {
		super.onStart()


	}

	private fun initLogOutToServer() {
		binding.logoutButton.setOnClickListener {
			auth.signOut()
			Log.d(TAG, "로그인아웃 정보를 전송 했습니다.")
			initLoginCheckFromServer()
		}
	}

	private fun initLoginCheckFromServer() {
		val isLogin = isLoginCheck()
		val isNotLogin = !isLoginCheck()
		when {
			isLogin -> { loginSuccess() }
			isNotLogin -> { logoutSuccess() }
		}
	}

	private fun initLogInToServer() {
		binding.loginButton.setOnClickListener {
			if (inputIsNotEmptyCheck() ) {
				val email: String = binding.emailEditText.text.toString()
				val password: String = binding.passwordEditText.text.toString()
				auth.signInWithEmailAndPassword(email, password)
					.addOnCompleteListener { Log.d(TAG, "로그인 정보를 전송 했습니다.") }
					.addOnSuccessListener { Log.d(TAG, "로그인에 성공 했습니다.")
						loginStatus = true
						initLoginCheckFromServer()
					}
					.addOnFailureListener { Log.d(TAG, "로그인에 실패 했습니다.") }
			}
		}
	}

	private fun initSignUpToServer() {
		binding.signUpButton.setOnClickListener {
			if (!inputIsNotEmptyCheck())
				return@setOnClickListener

			val email: String = binding.emailEditText.text.toString()
			val password: String = binding.passwordEditText.text.toString()
			auth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener { Log.d(TAG, "회원가입 정보를 전송 했습니다.") }
				.addOnSuccessListener { Log.d(TAG, "회원가입에 성공 했습니다.") }
				.addOnFailureListener { Log.d(TAG, "회원가입에 실패 했습니다.") }
		}

	}

	private fun inputIsNotEmptyCheck() =
		binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()

	private fun inputEmailAndPasswordCheck() {
		binding.emailEditText.addTextChangedListener {
			enableButtonClicked()
		}
		binding.passwordEditText.addTextChangedListener {
			enableButtonClicked()
		}
	}

	private fun enableButtonClicked() {
		if (!inputIsNotEmptyCheck()) {
			//binding.signUpButton.isEnabled = false
			binding.loginButton.isEnabled = false
			val nickname = auth.currentUser?.displayName
			Log.d(TAG, "입력이 비어있습니다.${inputIsNotEmptyCheck()}")
			Log.d(TAG, "로그인 유무 .${loginStatus} :  ${nickname}")



			return
		} else {
			//binding.signUpButton.isEnabled = true
			binding.loginButton.isEnabled = true
			Log.d(TAG, "입력이 비어있지 않습니다.${inputIsNotEmptyCheck()}")
		}

	}

	private fun isLoginCheck() = auth.currentUser != null

	private fun loginSuccess(){
		setTextEmailAndPassword()
		isVisibleLogOutButton()
	}
	private fun logoutSuccess(){
		isVisibleLoginButton()
		clearTextIdAndPassword()
	}
	private fun isVisibleLoginButton(){
		binding.loginButton.isVisible = true
		binding.logoutButton.isVisible = false
	}
	private fun isVisibleLogOutButton(){
		binding.logoutButton.isVisible = true
		binding.loginButton.isVisible = false
	}

	private fun setTextEmailAndPassword(){
		binding.emailEditText.setText(auth.currentUser?.email)
		binding.passwordEditText.setText("********")
	}

	private fun clearTextIdAndPassword(){
		binding.emailEditText.text.clear()
		binding.passwordEditText.text.clear()
	}

	companion object {
		const val TAG = "MyPageFragement"
	}


}