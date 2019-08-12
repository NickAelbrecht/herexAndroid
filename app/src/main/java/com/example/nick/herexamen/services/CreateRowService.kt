package com.example.nick.herexamen.services

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import com.example.nick.herexamen.R

class CreateRowService(private val context:Context) {

    private fun createTextView(text:String): TextView {
        val textView = TextView(context)
        textView.setPadding(16, 8, 8, 8)
        textView.textSize = 18f
        textView.text = text
        return textView
    }

    private fun createButton(): Button {
        val deleteButton = Button(context)
        deleteButton.layoutParams = TableRow.LayoutParams(48, 48)
        val gradDrawable = GradientDrawable()
        gradDrawable.cornerRadius = 75F
        deleteButton.background = gradDrawable
        deleteButton.setPadding(8, 0, 0, 8)
        deleteButton.setBackgroundResource(R.mipmap.vuilbak)
        return deleteButton
    }

    fun createRow(text:String):TableRow {
        val tableRow = TableRow(context)
        val textView = createTextView(text)
        val button = createButton()
        tableRow.addView(textView)
        tableRow.addView(button)
        return tableRow
    }
}