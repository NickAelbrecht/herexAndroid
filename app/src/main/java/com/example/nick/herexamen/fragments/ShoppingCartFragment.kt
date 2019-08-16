package com.example.nick.herexamen.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.nick.herexamen.MainActivity

import com.example.nick.herexamen.R
import com.example.nick.herexamen.adapters.MyCartAdapter
import com.example.nick.herexamen.services.AuthenticationService
import com.example.nick.herexamen.services.NetworkService
import com.example.nick.herexamen.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_shopping_cart.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShoppingCartFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShoppingCartFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ShoppingCartFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private val TAG = "ShoppingCartFragment"
    /**
     * [recipeViewModel] Het viewmodel dat wordt gebruikt voor de weergave van de verschillende recepten
     */
    private lateinit var recipeViewModel: RecipeViewModel
    /**
     * [myCartAdapter] De adapter die ervoor zorgt dat alle items correct worden weergegeven in de recyclerview
     */
    private lateinit var myCartAdapter: MyCartAdapter
    /**
     * [networkService] Controleert of er al dan niet netwerk aanwezig is
     */
    private lateinit var networkService: NetworkService
    /**
     * [authenticationService] Controleert of een gebruiker is ingelogd of niet
     */
    private lateinit var authenticationService: AuthenticationService


    /**
     *[onCreate] Wanneer het fragment voor de eerste keer wordt gecreëerd.
     * @param savedInstanceState: Het fragment zijn vorige opgeslagen state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate")
        recipeViewModel = ViewModelProviders.of(activity!!).get(RecipeViewModel::class.java)
        networkService = NetworkService()
        authenticationService = AuthenticationService(MainActivity())
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
        Log.d(TAG, "OnCreateView")
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_shopping_cart, container, false)

        myCartAdapter = MyCartAdapter(requireContext(), this)
        view.findViewById<Button>(R.id.cart_button_add).setOnClickListener { addNewRecipe() }
        view.cart_recycler.adapter = myCartAdapter
        view.cart_recycler.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    /**
     * [onStart] Wordt opgeroepen als het fragment zichtbaar is geworden
     */
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "OnStart")

        if (networkService.isNetworkAvailable(requireContext())) {
            recipeViewModel.getRecipesFromApi().observe(this, Observer { recepten ->
                Log.d(TAG, "API: $recepten")
                recepten?.let { myCartAdapter.setRecipes(it) }
            })
        } else {
            recipeViewModel.allRecipes.observe(this, Observer { recepten ->
                Log.d(TAG, "Room: $recepten")
                recepten?.let { myCartAdapter.setRecipes(it) }
            })
        }

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
        Log.d(TAG, "OnAttach")

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    /**
     * [addNewRecipe] Leidt de gebruiker naar het [NewRecipeFragment] waar deze een nieuw recept kan toevoegen.
     * Dit op voorwaarde dat als er netwerk beschikbaar is, dat de gebruiker ook ingelogd is
     */
    private fun addNewRecipe() {
        if (!authenticationService.checkSignedIn() && networkService.isNetworkAvailable(requireContext())) {
            Toast.makeText(context, "Gelieve in te loggen om iets toe te voegen", Toast.LENGTH_LONG)
                .show()
        } else {
            (activity as MainActivity).showAddNewRecipe()
        }
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
        @JvmStatic
        fun newInstance() = ShoppingCartFragment()
    }
}
