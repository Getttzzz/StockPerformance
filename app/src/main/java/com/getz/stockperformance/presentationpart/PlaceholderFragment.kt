package com.getz.stockperformance.presentationpart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.getz.stockperformance.R

class PlaceholderFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment()
        }
    }
}