package com.example.nick.herexamen.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nick.herexamen.R
import com.example.nick.herexamen.model.Recipe
import kotlinx.android.synthetic.main.cart_item.view.*

class MyCartAdapter(context: Context) :
    RecyclerView.Adapter<MyCartAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var recepten = emptyList<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recepten[position]
        holder.naam.text = item.title
        holder.aantalBenodigdheden.text = item.products.size.toString()
    }

    internal fun setRecipes(recipes: List<Recipe>) {
        this.recepten = recipes
        notifyDataSetChanged()

    }


    override fun getItemCount(): Int = recepten.size

    inner class ViewHolder(view: View) : RecyclerView.
    ViewHolder(view) {
        val naam: TextView = view.naam
        val aantalBenodigdheden: TextView = view.aantal_benodigdheden
    }

}