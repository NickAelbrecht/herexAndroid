package com.example.nick.herexamen.database.services

import android.text.TextUtils
import android.widget.TextView
import com.example.nick.herexamen.MainActivity
import com.example.nick.herexamen.R

class AddRecipeService(private var activity: MainActivity) {

    fun validateForm(titel: String, producten: List<String>, allergieen: List<String>?, soort: String): Boolean {
        var valid = true
        val fieldTitel: TextView = activity.findViewById(R.id.newrecipe_title)
        val fieldProducten: TextView = activity.findViewById(R.id.newrecipe_producten)
//        val fieldAllergieen: TextView? = activity.findViewById(R.id.newrecipe_allergieen)
        val fieldSoort: TextView = activity.findViewById(R.id.newrecipe_soort)

        if (TextUtils.isEmpty(titel)) {
            fieldTitel.error = "Vereist."
            valid = false
        } else {
            fieldTitel.error = null
        }

        if (producten.isEmpty()) {
            fieldProducten.error = "Vereist."
            valid = false
        } else {
            fieldProducten.error = null
        }

        if (TextUtils.isEmpty(soort)) {
            fieldSoort.error = "Vereist."
            valid = false
        } else {
            fieldSoort.error = null
        }

        return valid
    }
}