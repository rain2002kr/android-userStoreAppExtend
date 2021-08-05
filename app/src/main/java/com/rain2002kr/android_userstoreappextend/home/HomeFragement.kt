package com.rain2002kr.android_userstoreappextend.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rain2002kr.android_usedstoreappextend.base.UserDebug
import com.rain2002kr.android_usedstoreappextend.home.SellProduct
import com.rain2002kr.android_userstoreappextend.AddSellProductActivity
import com.rain2002kr.android_userstoreappextend.DBKey.Companion.DB_CHAT_USER
import com.rain2002kr.android_userstoreappextend.DBKey.Companion.DB_CHAT_USER_CHAT
import com.rain2002kr.android_userstoreappextend.DBKey.Companion.DB_SELL_PRODUCT
import com.rain2002kr.android_userstoreappextend.R
import com.rain2002kr.android_userstoreappextend.chatlist.ChatListItem
import com.rain2002kr.android_userstoreappextend.databinding.FragmentHomeBinding

class HomeFragement : Fragment(R.layout.fragment_home) {
	private lateinit var binding: FragmentHomeBinding
	private lateinit var dbSellProduct: DatabaseReference
	private lateinit var dbUsers: DatabaseReference
	private val adapter: SellProductAdapter by lazy {
		SellProductAdapter(itemClickListener = {
			// id 를 비교 해서, 내가 올린 아이템이라면 addItem 으로, 아닌 경우는 채팅으로
			val isLogin = isLoginCheck()
			val isNotLogin = !isLoginCheck()
			when {
				isLogin -> {
					when (it.sellerId) {
						auth.currentUser?.uid -> {
							//todo  addProduct Item 으로 이동 -> 업데이트 할수 있도록
							Log.d("HomeFrag", "내가 올린 아이템입니다.")
						}

						else -> {
							//todo 다른 판매자물품으로 채팅방 생성
							Log.d("HomeFrag", "다른 판매자물품 선택, 채팅방 생성")
							auth.currentUser?.let { user ->
								val buyerId = user.uid
								val chatRoom = ChatListItem(
									buyerId,
									it.sellerId,
									it.sellerNickName,
									it.title,
									it.price,
									it.imageUri,
									"마지막말....",
									System.currentTimeMillis()
								)

								dbUsers.child(buyerId).child(DB_CHAT_USER_CHAT).push()
									.setValue(chatRoom)
								dbUsers.child(it.sellerId).child(DB_CHAT_USER_CHAT).push()
									.setValue(chatRoom)

									.addOnSuccessListener { Log.d("HomeFrag", "채팅방 개설 성공") }
									.addOnFailureListener { Log.d("HomeFrag", "채팅방 개설 실패") }

							}


						}
					}

				}
				isNotLogin -> {
					Log.d("HomeFrag", "로그인후 사용해주세요.")
				}
			}


		})
	}
	private val lists: MutableList<SellProduct> by lazy { mutableListOf() }

	private val auth by lazy { Firebase.auth }


	private val listener = object : ChildEventListener {
		override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
			val sellProduct = snapshot.getValue(SellProduct::class.java)
			sellProduct ?: return

			lists.add(sellProduct)
			adapter.submitList(lists)
			adapter.notifyDataSetChanged()


		}

		override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

		override fun onChildRemoved(snapshot: DataSnapshot) {}

		override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

		override fun onCancelled(error: DatabaseError) {}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentHomeBinding.bind(view)
		initGetDataBaseData()
		initAddSellProduct()
		initGetUserChatListFromDatabase()


	}

	override fun onDestroy() {
		super.onDestroy()
		dbSellProduct.removeEventListener(listener)

	}

	private fun initGetDataBaseData() {
		dbSellProduct = Firebase.database.reference.child(DB_SELL_PRODUCT)
		lists.clear()
		binding.dataRecyclerView.adapter = adapter
		binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
		dbSellProduct.addChildEventListener(listener)

	}

	private fun initGetUserChatListFromDatabase() {
		dbUsers = Firebase.database.reference.child(DB_CHAT_USER)

	}

	private fun initAddSellProduct() {
		binding.addSellProductButton.setOnClickListener {
			val isLogin = isLoginCheck()
			val isNotLogin = !isLoginCheck()

			when {
				isLogin -> {
					val intent = Intent(requireContext(), AddSellProductActivity::class.java)
					startActivity(intent)
				}
				isNotLogin -> {
					Snackbar.make(it, "로그인후 사용해주세요.", Snackbar.LENGTH_LONG).show()
				}
			}


		}
	}


	private fun isLoginCheck() = auth.currentUser != null

}