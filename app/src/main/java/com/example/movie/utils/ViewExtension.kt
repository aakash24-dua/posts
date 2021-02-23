package com.example.movie.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.movie.R


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}



fun View.gone() {
    this.visibility = View.GONE
}

fun TextView.myTextView(text:String){
    this.text = text
    this.setTextColor(Color.WHITE)
    this.setPadding(10, 10, 10, 10)
    this.background = this.context.getDrawable(R.color.colorPrimary)
}