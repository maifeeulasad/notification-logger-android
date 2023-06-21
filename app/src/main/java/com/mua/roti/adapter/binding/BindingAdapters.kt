package com.mua.roti.adapter.binding


import android.graphics.Color
import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("warningColor")
    fun setWarningColor(button: Button, visible: Boolean) {
        button.setTextColor(if (visible) Color.GREEN else Color.RED)
    }

}