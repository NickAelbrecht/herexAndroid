package com.example.nick.herexamen.services

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import com.example.nick.herexamen.R

/**
 * [CreateRowService] Creëert dynamisch rijen met een [TextView] en een [Button]
 * @param context: Nodig voor het aanmaken van de TextViews en Button
 */
class CreateRowService(private val context:Context) {

    /**
     * [createTextView] Maakt een textView aan met de tekst
     * @param text: De tekst dat in de textView moet komen
     * @return [TextView]
     */
    private fun createTextView(text:String): TextView {
        val textView = TextView(context)
        textView.setPadding(16, 8, 8, 8)
        textView.textSize = 18f
        textView.text = text
        return textView
    }
    /**
     * [createButton] Maakt een Button
     * @return [Button]
     */
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

    /**
     * [createRow] Maakt een hele rij aan met textView en button
     * @param text: De tekst dat in de textview komt
     * @return [TableRow]
     */
    fun createRow(text:String):TableRow {
        val tableRow = TableRow(context)
        val textView = createTextView(text)
        val button = createButton()
        tableRow.addView(textView)
        tableRow.addView(button)
        return tableRow
    }
}