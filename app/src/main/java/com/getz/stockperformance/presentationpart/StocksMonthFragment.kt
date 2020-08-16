package com.getz.stockperformance.presentationpart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.getz.stockperformance.R
import kotlinx.android.synthetic.main.fragment_stocks_month.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StocksMonthFragment : Fragment(R.layout.fragment_stocks_month) {

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.stocksMonthLiveData.observe(viewLifecycleOwner, Observer { stocks ->
            chartViewMonthStocks.setupData(stocks.toMutableList())
        })
        mainViewModel.getMonthStocks()
    }

    companion object {
        fun newInstance(): StocksMonthFragment {
            return StocksMonthFragment()
        }
    }
}