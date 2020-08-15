package com.getz.stockperformance.presentationpart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.getz.stockperformance.R
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = getViewModel()

        setupViewPager()
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}