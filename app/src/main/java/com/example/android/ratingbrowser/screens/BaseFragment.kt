package com.example.android.ratingbrowser.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.ratingbrowser.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseFragment<T : BaseViewModel> : Fragment(R.layout.fragment_base), KodeinAware {
    override val kodein by kodein()

    protected abstract val viewModel: T

    @get:LayoutRes
    protected abstract val customLayout: Int

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner, Observer { directions ->
            findNavController().navigate(directions)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        inflater.inflate(customLayout, view?.findViewById(R.id.customRoot))
        return view
    }
}