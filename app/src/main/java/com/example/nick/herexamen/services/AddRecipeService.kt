package com.example.nick.herexamen.services

import android.text.TextUtils
import android.widget.TextView
import com.example.nick.herexamen.MainActivity
import com.example.nick.herexamen.R

/**
 * [AddRecipeService] Controleert of alle velden ingevuld zijn
 * @param activity: De activity wordt gebruikt om de juiste views te kunnen vinden
 */
class AddRecipeService(private var activity: MainActivity) {

    /**
     * [validateForm] De functie dat het formulier valideert
     * @param titel: De titel van het recept
     * @param producten: De producten van het recept
     * @param soort: De soort van het recept
     */
    fun validateForm(titel: String, producten: List<String>, soort: String): Boolean {
        var valid = true
        val fieldTitel: TextView = activity.findViewById(R.id.newrecipe_title)
        val fieldProducten: TextView = activity.findViewById(R.id.newrecipe_producten)
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