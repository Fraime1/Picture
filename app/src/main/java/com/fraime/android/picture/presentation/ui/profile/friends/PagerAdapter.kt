package com.fraime.android.picture.presentation.ui.profile.friends

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fraime.android.picture.presentation.ui.profile.friends.requestfirend.RequestFriendsFragment
import com.fraime.android.picture.presentation.ui.profile.friends.verifiedfriend.VerifyFriendsFragment

class PagerAdapter(fragment: FragmentActivity)  : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> VerifyFriendsFragment()
            else -> RequestFriendsFragment()
        }
    }

}