package com.example.android.ratingbrowser.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.atanana.common.StateWrapper
import com.atanana.common.StateWrapper.*
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.utils.setVisibility
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment(R.layout.fragment_base) {

    protected abstract val model: VM

    protected lateinit var binding: B

    protected abstract fun createBinding(container: ViewGroup): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding = createBinding(view!!.findViewById(R.id.customRoot))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            for (navDirections in model.navigation) {
                findNavController().navigate(navDirections)
            }
        }
    }

    protected fun <T> processState(state: StateWrapper<T>, block: (T) -> Unit) {
        when (state) {
            is Loading -> {
                customRoot.setVisibility(false)
                loading.setVisibility(true)
                errorMessage.setVisibility(false)
            }
            is Error -> {
                customRoot.setVisibility(false)
                loading.setVisibility(false)
                errorMessage.setVisibility(true)
                errorMessage.text = state.message
            }
            is Ok -> {
                customRoot.setVisibility(true)
                loading.setVisibility(false)
                errorMessage.setVisibility(false)
                block(state.data)
            }
        }
    }
}