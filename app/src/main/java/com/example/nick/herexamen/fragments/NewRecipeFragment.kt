package com.example.nick.herexamen.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.nick.herexamen.MainActivity

import com.example.nick.herexamen.R
import com.example.nick.herexamen.database.services.AddRecipeService
import com.example.nick.herexamen.model.Recipe
import com.example.nick.herexamen.viewmodels.RecipeViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


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
        val producten = view!!.findViewById<EditText>(R.id.newrecipe_producten).text.toString()
        val allergieen = view!!.findViewById<EditText>(R.id.newrecipe_allergieen).text.toString()
        val recipeSoort = view!!.findViewById<EditText>(R.id.newrecipe_soort).text.toString()

        if (addRecipeService.validateForm(recipeTitle, listOf(producten), listOf(allergieen), recipeSoort)) {
            val newRecipe = Recipe(recipeTitle, listOf(producten), listOf(allergieen), recipeSoort)

            //Log.d("RECIPES1", newRecipe.toString())

            recipeViewModel.insert(newRecipe)
            //Log.d("RECIPES1VWM", recipeViewModel.allRecipes.value.toString())
            showShoppingCart()
        }

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
