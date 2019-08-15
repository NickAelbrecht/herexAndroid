package com.example.nick.herexamen.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.nick.herexamen.MainActivity

import com.example.nick.herexamen.R
import com.example.nick.herexamen.services.AddRecipeService
import com.example.nick.herexamen.model.Recipe
import com.example.nick.herexamen.services.CreateRowService
import com.example.nick.herexamen.services.NetworkService
import com.example.nick.herexamen.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_new_recipe.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


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
    private lateinit var createRowService: CreateRowService
    private lateinit var networkService: NetworkService

    private var productenLijst: ArrayList<String> = ArrayList()
    private var allergieLijst: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        networkService = NetworkService()
        addRecipeService = AddRecipeService(activity as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_recipe, container, false)
        createRowService = CreateRowService(requireContext())
        view.findViewById<Button>(R.id.newrecipe_btn_add).setOnClickListener { addRecipe() }
        view.newrecipe_add_product.setOnClickListener { addProductToList() }
        view.newrecipe_add_allergie.setOnClickListener { addAllergieToList() }

        return view
    }

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
        val producten = productenLijst
        val allergieen = allergieLijst
        val recipeSoort = view!!.findViewById<EditText>(R.id.newrecipe_soort).text.toString()

        if (addRecipeService.validateForm(recipeTitle, producten, allergieen, recipeSoort)) {
            if (allergieen.isEmpty()) {
                allergieen.add("Geen")
            }
            val newRecipe = Recipe(recipeTitle, producten, allergieen, recipeSoort)

            recipeViewModel.insert(newRecipe)

            if (networkService.isNetworkAvailable(requireContext())) {
                recipeViewModel.insertRecipeApi(newRecipe).process { recipe, throwable ->
                    if (throwable != null) {
                        Toast.makeText(requireContext(), throwable.localizedMessage, Toast.LENGTH_LONG).show()
                    } else {
                        if (recipe == null) Toast.makeText(
                            requireContext(),
                            "No result returned",
                            Toast.LENGTH_LONG
                        ).show()
                        else Toast.makeText(
                            requireContext(),
                            recipe.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            showShoppingCart()
        }
    }

    private fun addProductToList() {
        val product = view!!.findViewById<EditText>(R.id.newrecipe_producten).text.toString()
        if (product.isEmpty()) {
            return
        }
        productenLijst.add(product)

        val tableRow = createRowService.createRow(product)
        val deleteButton = tableRow.getChildAt(1)

        val prodLayout = view!!.findViewById<TableLayout>(R.id.newrecipe_producten_linear)
        prodLayout.addView(tableRow)

        view!!.findViewById<EditText>(R.id.newrecipe_producten).text.clear()

        deleteButton.setOnClickListener {
            prodLayout.removeView(tableRow)
            productenLijst.remove(product)
        }

    }

    private fun addAllergieToList() {
        val allergie = view!!.findViewById<EditText>(R.id.newrecipe_allergieen).text.toString()
        if (allergie.isEmpty()) {
            return
        }
        allergieLijst.add(allergie)

        val tableRow = createRowService.createRow(allergie)
        val deleteButton = tableRow.getChildAt(1)

        val allergieLayout = view!!.findViewById<TableLayout>(R.id.newrecipe_allergieen_linear)
        allergieLayout.addView(tableRow)

        view!!.findViewById<EditText>(R.id.newrecipe_allergieen).text.clear()

        deleteButton.setOnClickListener {
            allergieLayout.removeView(tableRow)
            allergieLijst.remove(allergie)
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
         * this fragment
         *
         * @return A new instance of fragment NewRecipeFragment.
         */
        @JvmStatic
        fun newInstance() = NewRecipeFragment()
    }
}
