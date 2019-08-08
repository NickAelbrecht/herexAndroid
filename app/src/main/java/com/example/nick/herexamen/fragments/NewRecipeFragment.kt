package com.example.nick.herexamen.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.nick.herexamen.MainActivity

import com.example.nick.herexamen.R
import com.example.nick.herexamen.database.services.AddRecipeService
import com.example.nick.herexamen.model.Recipe
import com.example.nick.herexamen.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_new_recipe.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NewRecipeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NewRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class NewRecipeFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var addRecipeService: AddRecipeService
    private var productenLijst: ArrayList<String> = ArrayList()
    private var allergieLijst: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        addRecipeService = AddRecipeService(activity as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_recipe, container, false)
        view.findViewById<Button>(R.id.newrecipe_btn_add).setOnClickListener { addRecipe() }
        view.newrecipe_add_product.setOnClickListener { addProductToList() }
        view.newrecipe_add_allergie.setOnClickListener { addAllergieToList() }

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

    private fun addRecipe() {
        val recipeTitle = view!!.findViewById<EditText>(R.id.newrecipe_title).text.toString()
        //val producten = view!!.findViewById<EditText>(R.id.newrecipe_producten).text.toString()
        val producten = productenLijst
        //val allergieen = view!!.findViewById<EditText>(R.id.newrecipe_allergieen).text.toString()
        val allergieen = allergieLijst
        val recipeSoort = view!!.findViewById<EditText>(R.id.newrecipe_soort).text.toString()

        if (addRecipeService.validateForm(recipeTitle, producten, allergieen, recipeSoort)) {
            if(allergieen.isEmpty()) {
                allergieen.add("Geen")
            }
            val newRecipe = Recipe(recipeTitle, producten, allergieen, recipeSoort)

            //Log.d("RECIPES1", newRecipe.toString())

            recipeViewModel.insert(newRecipe)
            //Log.d("RECIPES1VWM", recipeViewModel.allRecipes.value.toString())
            showShoppingCart()
        }
    }

    private fun addProductToList() {
        val product = view!!.findViewById<EditText>(R.id.newrecipe_producten).text.toString()
        if (product.isEmpty()) {
            return
        }
        productenLijst.add(product)

        val textView = TextView(requireContext())
        textView.setPadding(16, 8, 8, 8)
        textView.textSize = 18f
        val prodLayout = view!!.findViewById<LinearLayout>(R.id.newrecipe_producten_linear)

        textView.text = product
        prodLayout.addView(textView)
        view!!.findViewById<EditText>(R.id.newrecipe_producten).text.clear()

    }

    private fun addAllergieToList() {
        val allergie = view!!.findViewById<EditText>(R.id.newrecipe_allergieen).text.toString()
        if (allergie.isEmpty()) {
            return
        }
        allergieLijst.add(allergie)

        val textView = TextView(requireContext())
        textView.setPadding(16, 8, 8, 8)
        textView.textSize = 18f
        val allergieLayout = view!!.findViewById<LinearLayout>(R.id.newrecipe_allergieen_linear)

        textView.text = allergie
        allergieLayout.addView(textView)
        view!!.findViewById<EditText>(R.id.newrecipe_allergieen).text.clear()
    }

    private fun showShoppingCart() {
        (activity as MainActivity).showCart()
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
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewRecipeFragment.
         */
        @JvmStatic
        fun newInstance() =
            NewRecipeFragment()
    }
}
