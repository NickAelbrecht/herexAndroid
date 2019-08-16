package com.example.nick.herexamen.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.nick.herexamen.MainActivity
import com.example.nick.herexamen.R
import com.example.nick.herexamen.fragments.RecipeDetailFragment
import com.example.nick.herexamen.model.Recipe
import kotlinx.android.synthetic.main.cart_item.view.*

class MyCartAdapter(context: Context, private val fragment: Fragment) :
    RecyclerView.Adapter<MyCartAdapter.ViewHolder>() {

    private val TAG = "CartAdapterTag"

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

        with(holder.naam) {
            tag = item
            setOnClickListener {
                val frag = RecipeDetailFragment.newInstance(item.title, item.products, item.allergies, item.kind)
                (fragment.activity as MainActivity).showRecipeDetailFragment(frag)
            }
        }

        with(holder.aantalBenodigdheden) {
            tag = item
            setOnClickListener {
                val frag = RecipeDetailFragment.newInstance(item.title, item.products, item.allergies, item.kind)
                (fragment.activity as MainActivity).showRecipeDetailFragment(frag)
            }
        }
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