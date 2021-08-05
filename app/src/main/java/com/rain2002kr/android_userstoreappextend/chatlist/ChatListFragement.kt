package com.rain2002kr.android_usedstoreappextend.chatlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rain2002kr.android_userstoreappextend.DBKey.Companion.DB_CHAT_USER
import com.rain2002kr.android_userstoreappextend.DBKey.Companion.DB_CHAT_USER_CHAT
import com.rain2002kr.android_userstoreappextend.R
import com.rain2002kr.android_userstoreappextend.chatdetail.ChatRoomActivity
import com.rain2002kr.android_userstoreappextend.chatlist.ChatListAdapter
import com.rain2002kr.android_userstoreappextend.chatlist.ChatListItem
import com.rain2002kr.android_userstoreappextend.databinding.FragmentChatlistBinding

class ChatListFragement : Fragment(R.layout.fragment_chatlist) {

	private lateinit var binding: FragmentChatlistBinding
	private val chatListAdapter: ChatListAdapter by lazy {
		ChatListAdapter(onClickListener = { chatRoom ->
			// 채팅방으로 이동 하는 코드
			context.let {
				var intent = Intent(it, ChatRoomActivity::class.java)
				intent.putExtra("chatKey", chatRoom.key)
				startActivity(intent)
			}
		})
	}

	private val auth: FirebaseAuth by lazy {
		Firebase.auth
	}
	private val chatRoomList = mutableListOf<ChatListItem>()



	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentChatlistBinding.bind(view)

		binding.chatListRecyclerView.adapter = chatListAdapter
		binding.chatListRecyclerView.layoutManager = LinearLayoutManager(context)

		chatRoomList.clear()

		auth.currentUser?.let{ user ->
			val chatDB = Firebase.database.reference.child(DB_CHAT_USER).child(user.uid)
				.child(DB_CHAT_USER_CHAT)

			chatDB.addListenerForSingleValueEvent(object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					snapshot.children.forEach {
						val model = it.getValue(ChatListItem::class.java)
						model ?: return
						chatRoomList.add(model)
					}
					chatListAdapter.submitList(chatRoomList)
					chatListAdapter.notifyDataSetChanged()
				}
				override fun onCancelled(error: DatabaseError) {

				}
			})

		}


}

private fun loginCheck(): Boolean {
	return auth.currentUser != null
}

override fun onResume() {
	super.onResume()
	chatListAdapter.notifyDataSetChanged()
}

}


//todo 채팅방 만들어주는 것은, 기존 틴더앱 활용
//home 에서 아이템 눌렀을때, seller id 와 item 을 추가
//chatListItem 데이터 클래스 만들기 : buyerId, sellerId, Key :timeMiliSecond + id값으로 키값, itemTitle
//chatListItem adpater, chatlist item view,
// firebase db 에 users 만들기, DB KEY 값
//