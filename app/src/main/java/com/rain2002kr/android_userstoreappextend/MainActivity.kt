package com.rain2002kr.android_userstoreappextend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rain2002kr.android_usedstoreappextend.mypage.MyPageFragement
import com.rain2002kr.android_usedstoreappextend.chatlist.ChatListFragement
import com.rain2002kr.android_userstoreappextend.databinding.ActivityMainBinding
import com.rain2002kr.android_userstoreappextend.home.HomeFragement

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val homeFragment = HomeFragement()
    private val chatListFragment = ChatListFragement()
    private val myPageFragement = MyPageFragement()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initChangeFragment()

    }

    private fun initChangeFragment(){
        // Todo 초기 화면
        replaceFragmentLayout(homeFragment)

        // todo navigation button 에 의한 화면 체인지
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> { replaceFragmentLayout(homeFragment) }
                R.id.chatList -> { replaceFragmentLayout(chatListFragment) }
                R.id.myPage -> { replaceFragmentLayout(myPageFragement) }
            }
            true
        }
    }

    private fun replaceFragmentLayout(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment )
                commit()
            }

    }

}