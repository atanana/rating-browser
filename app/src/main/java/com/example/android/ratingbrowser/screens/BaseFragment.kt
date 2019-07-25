package com.example.android.ratingbrowser.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseFragment<T : BaseViewModel> : Fragment(), KodeinAware {
    override val kodein by kodein()

    protected abstract val viewModel: T

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner, Observer { directions ->
            findNavController().navigate(directions)
        })
    }
}