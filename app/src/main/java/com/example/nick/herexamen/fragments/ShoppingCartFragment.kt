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
import com.example.nick.herexamen.MainActivity

import com.example.nick.herexamen.R
import com.example.nick.herexamen.adapters.MyCartAdapter
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
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var myCartAdapter: MyCartAdapter
    private lateinit var networkService: NetworkService


    //private var recipes: List<Recipe>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate")
        recipeViewModel = ViewModelProviders.of(activity!!).get(RecipeViewModel::class.java)
        networkService = NetworkService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_shopping_cart, container, false)

        myCartAdapter = MyCartAdapter(requireContext(), this)
        view.findViewById<Button>(R.id.cart_button_add).setOnClickListener { addNewRecipe() }
        view.cart_recycler.adapter = myCartAdapter
        view.cart_recycler.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onStart() {
        super.onStart()
        if (networkService.isNetworkAvailable(requireContext())) {
            recipeViewModel.getRecipesFromApi().observe(this, Observer { recepten ->
                recepten?.let { myCartAdapter.setRecipes(it) }
            })
        } else {
            recipeViewModel.allRecipes.observe(this, Observer { recepten ->
                recepten?.let { myCartAdapter.setRecipes(it) }
            })
        }

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


    private fun addNewRecipe() {
        (activity as MainActivity).showAddNewRecipe()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroy() {
        super.onDestroy()
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
        @JvmStatic
        fun newInstance() = ShoppingCartFragment()
    }
}
