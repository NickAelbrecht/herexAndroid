package com.example.nick.herexamen.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.nick.herexamen.MainActivity

import com.example.nick.herexamen.R
import com.example.nick.herexamen.services.AuthenticationService
import kotlinx.android.synthetic.main.fragment_register.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RegisterFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RegisterFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    /**
     * [authenticationService] Wordt gebruikt voor de gebruiker te kunnen registreren
     * alsook controle of alle ingevulde gegevens correct zijn
     */
    private lateinit var authenticationService: AuthenticationService

    /**
     *[onCreate] Wanneer het fragment voor de eerste keer wordt gecreëerd.
     * @param savedInstanceState: Het fragment zijn vorige opgeslagen state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authenticationService = AuthenticationService(activity as MainActivity)

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
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        view.button_register_complete.setOnClickListener { registerUser() }

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
     * [registerUser] Registreert een gebruiker met de ingevulde gegevens, na controle of alle gegevens correct zijn ingevuld
     */
    private fun registerUser() {
        val naam: String = view!!.findViewById<EditText>(R.id.register_naam).text.toString()
        val email: String = view!!.findViewById<EditText>(R.id.register_email).text.toString()
        val paswoord: String = view!!.findViewById<EditText>(R.id.register_password).text.toString()
        val confirmPaswoord: String = view!!.findViewById<EditText>(R.id.register_password_confirm).text.toString()
        authenticationService.createUser(naam, email, paswoord, confirmPaswoord, this)
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
         * @return A new instance of fragment RegisterFragment.
         */
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}
