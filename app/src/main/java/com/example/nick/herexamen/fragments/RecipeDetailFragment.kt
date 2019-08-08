package com.example.nick.herexamen.fragments

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.nick.herexamen.R
import com.example.nick.herexamen.model.Recipe
import com.example.nick.herexamen.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_recipe_detail.view.*
import java.lang.NullPointerException
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RecipeDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RecipeDetailFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var recipeByTitle: Recipe


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        view.recipe_detail_title.text = arguments!!.getString("title")
        view.recipe_detail_soort.text = arguments!!.getString("soort")
        addProducts(view)
        addAllergies(view)
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    private fun addAllergies(view: View) {
        val allergieen = arguments!!.getStringArrayList("allergieen")

        for (allergie in allergieen) {
            val allerLayout = view.findViewById<TableLayout>(R.id.recipe_detail_aller_layout)

            val tableRow = TableRow(requireContext())

            val textView = TextView(requireContext())
            textView.setPadding(16, 8, 8, 8)
            textView.textSize = 18f
            textView.text = allergie

            val deleteButton = Button(requireContext())
            val gradDrawable = GradientDrawable()
            gradDrawable.cornerRadius = 50F
            gradDrawable.setColor(Color.RED)
            deleteButton.background = gradDrawable
            deleteButton.text = "-"

            tableRow.addView(textView)
            tableRow.addView(deleteButton)
            allerLayout.addView(tableRow)

            deleteButton.setOnClickListener {
                allerLayout.removeView(tableRow)
            }
        }
    }

    private fun addProducts(view: View) {
        val producten = arguments!!.getStringArrayList("producten")

        for (product in producten) {
            val prodLayout = view.findViewById<LinearLayout>(R.id.recipe_detail_prod_layout)

            val tableRow = TableRow(requireContext())

            val textView = TextView(requireContext())
            textView.setPadding(16, 8, 8, 8)
            textView.textSize = 18f
            textView.text = product

            val deleteButton = Button(requireContext())
            val gradDrawable = GradientDrawable()
            gradDrawable.cornerRadius = 50F
            gradDrawable.setColor(Color.RED)
            deleteButton.background = gradDrawable
            deleteButton.text = "-"

            tableRow.addView(textView)
            tableRow.addView(deleteButton)
            prodLayout.addView(tableRow)

            deleteButton.setOnClickListener {
                prodLayout.removeView(tableRow)
                deleteProductFromRecipe(tableRow, producten)
            }
        }
    }

    private fun deleteProductFromRecipe(tableRow: TableRow, producten: ArrayList<String>) {
        val title = (tableRow.getChildAt(0) as TextView).text
        producten.remove(title)
        arguments!!.putStringArrayList("producten", producten)

        val receptTitle = arguments!!.getString("title")
        recipeByTitle = recipeViewModel.findByTitle(receptTitle)
        updateRecipe(recipeByTitle)
    }


    private fun updateRecipe(recipe: Recipe) {
        val producten = arguments!!.getStringArrayList("producten")
        //Log.d("DELETE", "voor update: $recipe ")
        recipe.products = producten
        recipeViewModel.updateRecipe(recipe)
        //Log.d("DELETE", "after update: ${recipeViewModel.findByTitle(recipe.title)}")

    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment RecipeDetailFragment.
         */
        @JvmStatic
        fun newInstance(
            title: String,
            producten: List<String>,
            allergieen: List<String>,
            soort: String
        ): RecipeDetailFragment {
            val frag = RecipeDetailFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("soort", soort)
            args.putStringArrayList("producten", producten.map { product -> product } as ArrayList<String>)
            args.putStringArrayList("allergieen", allergieen.map { allergie -> allergie } as ArrayList<String>)
            frag.arguments = args
            return frag
        }
    }
}
