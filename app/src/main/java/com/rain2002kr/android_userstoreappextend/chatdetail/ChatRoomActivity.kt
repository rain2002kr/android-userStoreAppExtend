package com.rain2002kr.android_userstoreappextend.chatdetail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rain2002kr.android_userstoreappextend.DBKey
import com.rain2002kr.android_userstoreappextend.DBKey.Companion.DB_CHATS
import com.rain2002kr.android_userstoreappextend.databinding.ActivityChatRoomBinding

class ChatRoomActivity :AppCompatActivity() {
	private val binding:ActivityChatRoomBinding by lazy { ActivityChatRoomBinding.inflate(layoutInflater) }
	private val auth = Firebase.auth
	private val chatList = mutableListOf<ChatItem>()
	private val chatItemAdapter:ChatItemAdapter by lazy {ChatItemAdapter(onClickListener = {
		if(auth.currentUser?.displayName != it.senderNickName){
			changeTextMessageColor()
		}


	})}
	lateinit var chatDB : DatabaseReference


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		val chatKey = intent.getLongExtra("chatKey", -1)
		chatDB = Firebase.database.reference.child(DB_CHATS).child("$chatKey")

		chatDB.addChildEventListener(object:ChildEventListener{
			override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
				val chatItem = snapshot.getValue(ChatItem::class.java)
				chatItem ?: return

				chatList.add(chatItem)
				chatItemAdapter.submitList(chatList)
				chatItemAdapter.notifyDataSetChanged()
			}

			override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

			}

			override fun onChildRemoved(snapshot: DataSnapshot) {

			}

			override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

			}

			override fun onCancelled(error: DatabaseError) {

			}
		})

		binding.chatRecyclerView.adapter = chatItemAdapter
		binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)

		binding.sendButton.setOnClickListener {
			val chatItem = ChatItem(senderId = auth.currentUser?.uid.toString(), senderNickName = auth.currentUser?.displayName.toString() ,message = binding.messageEditText.text.toString() )
			chatDB.push().setValue(chatItem)

		}

	}
	private fun changeTextMessageColor(){
		binding.messageEditText.setBackgroundColor(getColor(android.R.color.holo_blue_bright))
	}

}