package com.example.nick.herexamen.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nick.herexamen.MainActivity

import com.example.nick.herexamen.R
import kotlinx.android.synthetic.main.fragment_user.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class UserFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private var TAG = "LoginFragment"
    /**
     *[onCreate] Wanneer het fragment voor de eerste keer wordt gecreëerd.
     * @param savedInstanceState: Het fragment zijn vorige opgeslagen state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        val view: View = inflater.inflate(R.layout.fragment_user, container, false)
        view.btn_login.setOnClickListener { showLogin() }
        view.btn_register.setOnClickListener { showRegister() }
        return view
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
     * [onDetach] Is de laatste methode die wordt opgeroepen, nog na [onDestroy]. Het laat weten dat het fragment niet meer aan de activity hangt
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * [showLogin] Leidt de gebruiker naar de inlogpagina [LoginFragment]
     */
    private fun showLogin() {
        Log.d(TAG, "login clicked")
        (activity as MainActivity).showLogin()
    }

    /**
     * [showRegister] Leidt de gebruiker naar de registratiepagina [RegisterFragment]
     */
    private fun showRegister() {
        Log.d(TAG, "register clicked")
        (activity as MainActivity).showRegister()
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
         * @return A new instance of fragment UserFragment.
         */
        @JvmStatic
        fun newInstance() = UserFragment()
    }
}
