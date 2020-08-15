package com.getz.stockperformance.presentationpart

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.getz.stockperformance.R

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private val TAB_TITLES = arrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val STOCK_WEEK_TAB_POSITION = 0
        const val STOCK_MONTH_TAB_POSITION = 1
    }

    override fun getItem(position: Int): Fragment = when (position) {
        STOCK_WEEK_TAB_POSITION -> StocksWeekFragment.newInstance()
//        STOCK_MONTH_TAB_POSITION -> StocksWeekFragment.newInstance()
        else -> throw IllegalArgumentException("Wrong position")
    }

    override fun getPageTitle(position: Int) = context.resources.getString(TAB_TITLES[position])

    override fun getCount() = 1
}