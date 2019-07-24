package com.example.nick.herexamen

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.nick.herexamen.authentication.AuthenticationService
import com.example.nick.herexamen.fragments.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeFragment.OnFragmentInteractionListener,
    UserFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener,
    ShoppingCartFragment.OnFragmentInteractionListener{

    private var TAG: String =  "MainActivityTag"
    private lateinit var authenticationService:AuthenticationService

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(HomeFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_recepten -> {
                switchFragment(ShoppingCartFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                if(authenticationService.checkSignedIn()) {
                    Log.d(TAG, "user logged in ${authenticationService.getAuth().currentUser}")
                    switchFragment(ProfileFragment.newInstance())
                } else {
                    Log.d(TAG, "user NOT logged in")
                    switchFragment(UserFragment.newInstance())
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        authenticationService = AuthenticationService(this)
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .setTransition(1)
            .commit()
    }

    fun showRegister() {
        switchFragment(RegisterFragment.newInstance())
    }

    fun showLogin() {
        switchFragment(LoginFragment.newInstance())
    }

    fun showHome() {
        switchFragment(HomeFragment.newInstance())
    }


    fun updateUi(user: FirebaseUser?) {
        if(user != null) {
            Log.d(TAG, "user: " + user.email)
            switchFragment(ProfileFragment.newInstance())
        } else {
            Log.d(TAG, "user null?")
        }
    }




}

