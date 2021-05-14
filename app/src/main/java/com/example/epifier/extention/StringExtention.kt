package com.example.epifier.extention

import android.text.Html
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.view.View
import com.example.epifier.R

fun String.toSpannable(): SpannableStringBuilder {
    return SpannableStringBuilder(Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY))
}

fun SpannableStringBuilder.addUrlHandler(onClicked: (url: String) -> Unit = {}, color: Int): SpannableStringBuilder {
    this.getSpans(0, this.length, URLSpan::class.java).forEach {
        setSpan(object : URLSpan(it.url) {
            override fun onClick(widget: View) {
                onClicked(url)
            }
        }, getSpanStart(it), getSpanEnd(it), getSpanFlags(it))
        removeSpan(it)
    }
    this.getSpans(0, this.length, URLSpan::class.java).forEach {
        setSpan(ForegroundColorSpan(color), getSpanStart(it), getSpanEnd(it), getSpanFlags(it))
    }
    return this
}