package com.example.android.ratingbrowser.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun Context.inflate(
    @LayoutRes layout: Int,
    parent: ViewGroup? = null,
    attachToRoot: Boolean = false
): View {
    val inflater = LayoutInflater.from(this)
    return inflater.inflate(layout, parent, attachToRoot)
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View =
    context.inflate(layout, this, attachToRoot)

fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.inflater() = LayoutInflater.from(context)