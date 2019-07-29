package com.example.nick.herexamen.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.nick.herexamen.MainActivity

import com.example.nick.herexamen.R
import com.example.nick.herexamen.adapters.MyCartAdapter
import com.example.nick.herexamen.model.Recipe
import kotlinx.android.synthetic.main.fragment_shopping_cart.*


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

    private val recipes = listOf(
        Recipe("Croques", listOf("Kaas", "Hesp", "Brood"), listOf("Gluten"), "Brood"),
        Recipe("Smos", listOf("Kaas", "Hesp", "Brood", "Tomaten", "Wortels"), listOf("Gluten"), "Brood"),
        Recipe("Smos", listOf("Kaas", "Hesp", "Brood", "Tomaten", "Wortels"), listOf("Gluten"), "Brood"),
        Recipe("Smos", listOf("Kaas", "Hesp", "Brood", "Tomaten", "Wortels"), listOf("Gluten"), "Brood")


    )

    private var recyclerAdapter: MyCartAdapter = MyCartAdapter(recipes)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        view.findViewById<Button>(R.id.cart_button_add).setOnClickListener { addNewRecipe() }
        val recycler = view.findViewById<RecyclerView>(R.id.cart_recycler)
        recycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }

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


    fun updateRecipes(recipes: List<Recipe>?) {
        Log.d("RECIPES2", recipes.toString())

        recipes?.let { recyclerAdapter.setRecipes(it); recyclerAdapter.notifyDataSetChanged() }
    }

    private fun addNewRecipe() {
        (activity as MainActivity).showAddNewRecipe()
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
        @JvmStatic
        fun newInstance() =
            ShoppingCartFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
