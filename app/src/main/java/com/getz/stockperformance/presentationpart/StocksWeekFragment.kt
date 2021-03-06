package com.getz.stockperformance.presentationpart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.getz.stockperformance.R
import kotlinx.android.synthetic.main.fragment_stocks_week.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StocksWeekFragment : Fragment(R.layout.fragment_stocks_week) {

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.stocksWeekLiveData.observe(viewLifecycleOwner, Observer { stocks ->
            chartViewWeekStocks.setupData(stocks.toMutableList())
        })
        mainViewModel.getWeekStocks()
    }

    companion object {
        fun newInstance(): StocksWeekFragment {
            return StocksWeekFragment()
        }
    }
}