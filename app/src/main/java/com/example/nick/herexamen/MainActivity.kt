package com.example.nick.herexamen

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.nick.herexamen.authentication.AuthenticationService
import com.example.nick.herexamen.fragments.HomeFragment
import com.example.nick.herexamen.fragments.LoginFragment
import com.example.nick.herexamen.fragments.UserFragment
import com.example.nick.herexamen.fragments.RegisterFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeFragment.OnFragmentInteractionListener,
    UserFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    private lateinit var authenticationService:AuthenticationService
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(HomeFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_oefeningen -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_login -> {
                switchFragment(UserFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        /*if(savedInstanceState != null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, HomeFragment.newInstance())
        }*/
        authenticationService = AuthenticationService(this)
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun switchFragment(fragment: Fragment) {
        //val old_frag: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .setTransition(1)
            .commit()

    }

    fun showRegister() {
        switchFragment(RegisterFragment.newInstance("str1", "str2"))
    }

    fun showLogin() {
        switchFragment(LoginFragment.newInstance("str1", "str2"))
    }

    fun registerUser(email: String, paswoord: String) {
        var user = authenticationService.createUser(email, paswoord)
        Toast.makeText(baseContext, "Registered", Toast.LENGTH_LONG).show()
        Log.d("ACTI_REG", user.toString())
    }


}

