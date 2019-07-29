package com.example.nick.herexamen.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nick.herexamen.R
import com.example.nick.herexamen.fragments.ShoppingCartFragment
import com.example.nick.herexamen.model.Recipe
import kotlinx.android.synthetic.main.cart_item.view.*

class MyCartAdapter(private var recepten: List<Recipe>):RecyclerView.Adapter<MyCartAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recepten[position]
        holder.naam.text = item.title
        holder.aantalBenodigdheden.text = item.products.size.toString()
    }

    internal fun setRecipes(recipes:List<Recipe>?) {
        if(!recipes.isNullOrEmpty()){
            Log.d("RECIPES3", recipes.toString())
            this.recepten = recipes
        }
    }


    override fun getItemCount(): Int = recepten.size

    inner class ViewHolder(view: View) : RecyclerView.
    ViewHolder(view) {
        val naam: TextView = view.naam
        val aantalBenodigdheden: TextView = view.aantal_benodigdheden
    }

}