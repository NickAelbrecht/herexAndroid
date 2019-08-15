package com.example.nick.herexamen

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.nick.herexamen.services.AuthenticationService
import com.example.nick.herexamen.fragments.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeFragment.OnFragmentInteractionListener,
    UserFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener,
    LoginFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener,
    ShoppingCartFragment.OnFragmentInteractionListener, NewRecipeFragment.OnFragmentInteractionListener, RecipeDetailFragment.OnFragmentInteractionListener {

    private var TAG: String = "MainActivityTag"
    private lateinit var authenticationService: AuthenticationService


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(HomeFragment.newInstance(), "HomeFragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_recepten -> {
                switchFragment(ShoppingCartFragment.newInstance(), "ShoppingCartFragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                if (authenticationService.checkSignedIn()) {
                    Log.d(TAG, "user logged in ${authenticationService.getAuth().currentUser}")
                    switchFragment(ProfileFragment.newInstance(), "ProfileFragment")
                } else {
                    Log.d(TAG, "user NOT logged in")
                    switchFragment(UserFragment.newInstance(), "UserFragment")
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {

            super.onCreate(savedInstanceState)

            if (savedInstanceState == null) {
                showHome()
            }
            setContentView(R.layout.activity_main)
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

            authenticationService = AuthenticationService(this)
        }catch (exec:Exception) {
            Log.e(TAG, "error oncreate: ${exec.message}", exec)
            throw exec
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun switchFragment(fragment: Fragment, tag: String) {
        val fragTransaction = supportFragmentManager.beginTransaction()
        val currentFrag = supportFragmentManager.primaryNavigationFragment
        if (currentFrag != null) {
            fragTransaction.hide(currentFrag).commit()
        }

        if (checkBackStack(tag)) {
            Log.d("SWITCH", "NEW CREATED: $tag")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(tag)
                .setTransition(1)
                .commit()
        } else {
            Log.d("SWITCH", "REUSE SHOW: $tag")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,supportFragmentManager.findFragmentByTag(tag)!!, tag)
                .addToBackStack(tag)
                .setTransition(1)
                .commit()
        }
    }

    private fun checkBackStack(tag: String): Boolean {
        return supportFragmentManager.findFragmentByTag(tag) == null
    }


    fun showRegister() {
        switchFragment(RegisterFragment.newInstance(), "RegisterFragment")
    }

    fun showLogin() {
        switchFragment(LoginFragment.newInstance(), "LoginFragment")
    }

    fun showHome() {
        switchFragment(HomeFragment.newInstance(), "HomeFragment")
    }

    fun showAddNewRecipe() {
        switchFragment(NewRecipeFragment.newInstance(), "NewRecipeFragment")
    }

    fun showCart() {
        switchFragment(ShoppingCartFragment.newInstance(), "ShoppingCartFragment")
    }

    fun showRecipeDetailFragment(fragment:RecipeDetailFragment) {
        //Enkel voor detail verplicht nieuw fragment gebruiken, anders worden de variabelen daar niet aangepast door de newInstance
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, "RecipeDetailFragment")
            .addToBackStack("RecipeDetailFragment")
            .setTransition(1)
            .commit()
    }

    private fun detachFragment(fragment: Fragment) {
        val fragTransaction = supportFragmentManager.beginTransaction()
        fragTransaction.detach(fragment).commit()
    }


    fun updateUi(user: FirebaseUser?, fragment: Fragment) {
        if (user != null) {
            Log.d(TAG, "user: " + user.email)
            detachFragment(fragment)
            switchFragment(ProfileFragment.newInstance(), "ProfileFragment")
        } else {
            Log.d(TAG, "user null?")
        }
    }


}

