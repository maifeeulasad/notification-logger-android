package com.mua.roti.adapter.binding


import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibility(view: View, visible: Boolean) {
        if (visible)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

}