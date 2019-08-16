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
import com.example.nick.herexamen.model.Recipe
import com.example.nick.herexamen.services.AuthenticationService
import com.example.nick.herexamen.services.CreateRowService
import com.example.nick.herexamen.services.NetworkService
import com.example.nick.herexamen.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_recipe_detail.view.*
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
    private val TAG = "RecipeDetailTag"

    private var listener: OnFragmentInteractionListener? = null
    /**
     * [recipeViewModel] Is het viewmodel dat wordt gebruikt om aanpassingen aan het recept door te voeren
     */
    private lateinit var recipeViewModel: RecipeViewModel
    /**
     * [recipeByTitle] Het opgehaalde recept van de database waarvoor deze detailpagina wordt gevraagd
     */
    private lateinit var recipeByTitle: Recipe
    /**
     * [createRowService] Zorgt voor de dynamische aanmaak van de rijen mbt producten en allergieen
     */
    private lateinit var createRowService: CreateRowService
    /**
     * [networkService] Controleert of er een netwerkverbinding is zodat de backend kan gebruikt worden en anders de lokale room database
     */
    private lateinit var networkService: NetworkService
    /**
     * [authenticationService] Zorgt voor de controle dat enkel een ingelogde gebruiker aanpassingen kan doen
     */
    private lateinit var authenticationService: AuthenticationService

    /**
     *[onCreate] Wanneer het fragment voor de eerste keer wordt gecreëerd.
     * @param savedInstanceState: Het fragment zijn vorige opgeslagen state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        networkService = NetworkService()
    }

    /**
     * [onCreateView] Wanneer de UI van het fragment voor de eerste keer wordt getekend
     * @param inflater: De inflater die de layout 'inflate'
     * @param container: De container waar de layout moet in terechtkomen
     * @param savedInstanceState: De vorige opgeslagen toestand
     * @return een [View]
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        view.recipe_detail_title.text = arguments!!.getString("title")
        view.recipe_detail_soort.text = arguments!!.getString("soort")

        createRowService = CreateRowService(requireContext())
        authenticationService = AuthenticationService(MainActivity())

        addProducts(view)
        addAllergies(view)
        val receptTitle = arguments!!.getString("title")
        if (!receptTitle.isNullOrEmpty()) {
            if (networkService.isNetworkAvailable(view.context)) {
                recipeViewModel.getRecipeByTitleApi(receptTitle).process { recipe, throwable ->
                    if (throwable != null) {
                        Log.d(TAG, throwable.localizedMessage)
                    } else {
                        if (recipe == null) {
                            Log.d(TAG, "No result returned(recept")
                        } else recipeByTitle = recipe
                    }
                }
            } else {
                recipeByTitle = recipeViewModel.findByTitle(receptTitle)
            }
            view.findViewById<Button>(R.id.recipe_detail_delete).setOnClickListener { deleteRecipe(receptTitle) }
        }
        return view
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    /**
     * [onAttach] De allereerste methode die wordt opgeroepen. Laat weten dat we aan een activity vasthangen
     * @param context: De activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    /**
     * [addAllergies] Voegt dynamisch de tableRows toe. Er wordt ook een onclicklistener toegevoegd voor de vuilbak(delete)
     * @param view: De view wordt gebruikt voor de TableLayout te zoeken
     */
    private fun addAllergies(view: View) {
        val allergieen = arguments!!.getStringArrayList("allergieen")
        if (!allergieen.isNullOrEmpty()) {
            for (allergie in allergieen) {
                val allerLayout = view.findViewById<TableLayout>(R.id.recipe_detail_aller_layout)

                val tableRow = createRowService.createRow(allergie)
                val deleteButton = tableRow.getChildAt(1)
                allerLayout.addView(tableRow)

                deleteButton.setOnClickListener {

                    when {
                        (!authenticationService.checkSignedIn() && networkService.isNetworkAvailable(requireContext())) -> {
                            Toast.makeText(
                                context,
                                "Gelieve in te loggen voor deze allergie te verwijderen",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        else -> {
                            allerLayout.removeView(tableRow)
                            deleteAllergieFromRecipe(tableRow, allergieen)
                        }
                    }
                }
            }
        }
    }

    /**
     * [addProducts] Voegt dynamisch de tableRows toe. Er wordt ook een onclicklistener toegevoegd voor de vuilbak(delete)
     * @param view: De view wordt gebruikt voor de TableLayout te zoeken
     */
    private fun addProducts(view: View) {
        val producten = arguments!!.getStringArrayList("producten")
        if (!producten.isNullOrEmpty()) {
            for (product in producten) {
                val prodLayout = view.findViewById<TableLayout>(R.id.recipe_detail_prod_layout)

                val tableRow = createRowService.createRow(product)
                val deleteButton = tableRow.getChildAt(1)
                prodLayout.addView(tableRow)

                deleteButton.setOnClickListener {
                    when {
                        (!authenticationService.checkSignedIn() && networkService.isNetworkAvailable(requireContext())) -> {
                            Toast.makeText(
                                context,
                                "Gelieve in te loggen voor dit product te verwijderen",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        producten.size < 2 -> {
                            Toast.makeText(context, "Recept moet minstens 1 product hebben", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            prodLayout.removeView(tableRow)
                            deleteProductFromRecipe(tableRow, producten)
                        }
                    }

                }
            }
        }
    }

    /**
     * [deleteProductFromRecipe] Verwijdert een bepaald product van het recept
     * @param tableRow: De tablerow die moet verwijderd worden
     * @param producten: De lijst met producten die het recept bevat
     */
    private fun deleteProductFromRecipe(tableRow: TableRow, producten: ArrayList<String>) {
        val title = (tableRow.getChildAt(0) as TextView).text
        producten.remove(title)
        arguments!!.putStringArrayList("producten", producten)

        updateRecipe(recipeByTitle)
    }

    /**
     * [deleteAllergieFromRecipe] Verwijdert een bepaalde allergie van het recept
     * @param tableRow: De tablerow die moet verwijderd worden
     * @param allergieen: De lijst met allergieen die het recept bevat
     */
    private fun deleteAllergieFromRecipe(tableRow: TableRow, allergieen: ArrayList<String>) {
        val title = (tableRow.getChildAt(0) as TextView).text
        allergieen.remove(title)
        arguments!!.putStringArrayList("allergieen", allergieen)

        updateRecipe(recipeByTitle)
    }

    /**
     * [updateRecipe] Update een bepaald recept als er aanpassingen zijn gedaan aan de producten of allergieen
     * @param recipe: Het recept dat moet geupdate worden
     */
    private fun updateRecipe(recipe: Recipe) {
        val producten = arguments!!.getStringArrayList("producten")
        val allergieen = arguments!!.getStringArrayList("allergieen")
        if (!producten.isNullOrEmpty()) {
            recipe.products = producten
            recipe.allergies = allergieen
            if (networkService.isNetworkAvailable(requireContext())) {
                recipeViewModel.updateRecipeApi(recipe).process { recipe, throwable ->
                    if (throwable != null) {
                        Log.d(TAG, throwable.localizedMessage)
                    } else {
                        if (recipe == null) {
                            Log.d(TAG, "No result returned(update)")
                        } else {
                            Log.d(TAG, "Succes update: $recipe")
                        }
                    }
                }
            } else {
                recipeViewModel.updateRecipe(recipe)
            }
        }
    }

    /**
     * [deleteRecipe] Verwijdert een bepaald recept met de bijhorende titel
     * @param title: De titel van het recept dat moet verwijderd worden
     */
    private fun deleteRecipe(title: String) {
        if (!authenticationService.checkSignedIn() && networkService.isNetworkAvailable(requireContext())) {
            Toast.makeText(context, "Gelieve in te loggen voor dit recept verwijderen", Toast.LENGTH_LONG)
                .show()
        } else if (networkService.isNetworkAvailable(requireContext())) {
            recipeViewModel.deleteRecipeByTitleApi(title).process { response, throwable ->
                if (throwable != null) {
                    Log.d(TAG, throwable.localizedMessage)
                } else if (!response.isNullOrEmpty()) {
                    Log.d(TAG, response)
                }
            }
        } else {
            recipeViewModel.deleteByTitle(title)
        }
        (activity as MainActivity).showCart()
    }

    /**
     * [onDetach] Is de laatste methode die wordt opgeroepen, nog na [onDestroy]. Het laat weten dat het fragment niet meer aan de activity hangt
     */
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
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @param title: De titel van het recept
         * @param producten: De lijst van producten dat het recept bevat
         * @param allergieen: De lijst van allergieen dat het recept bevat
         * @param soort: De soort van het recept
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
