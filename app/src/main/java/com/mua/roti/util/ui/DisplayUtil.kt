package com.mua.roti.util.ui

import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.widget.TextView

object DisplayUtil {
    // ref:
    //  - https://stackoverflow.com/a/53573169/10305444
    //  - https://stackoverflow.com/a/53573169/10305444
    fun setHighLightedText(tv: TextView, textToHighlight: String) {
        val tvt = tv.text.toString()
        var ofe = tvt.indexOf(textToHighlight)
        val wordToSpan: Spannable = SpannableString(tv.text)
        var ofs = 0
        while (ofs < tvt.length && ofe != -1) {
            ofe = tvt.indexOf(textToHighlight, ofs)
            if (ofe == -1) break else {
                wordToSpan.setSpan(
                    BackgroundColorSpan(-0x100),
                    ofe,
                    ofe + textToHighlight.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE)
            }
            ofs = ofe + 1
        }
    }
}