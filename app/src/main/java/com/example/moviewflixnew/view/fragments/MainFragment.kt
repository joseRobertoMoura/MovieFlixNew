package com.example.moviewflixnew.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainFragment : Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragments("list")
        navController = Navigation.findNavController(view)
        val bttom_bar = view.findViewById<BottomNavigationView>(R.id.bottom_bar)
        bttom_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    logout()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.list -> {
                    fragments("list")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.search -> {
                    fragments("search")
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }

    }

    private fun backMain() {
        navController.navigate(R.id.action_mainFragment_to_loginFragment)
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        backMain()
    }

    private fun fragments(nameFragment: String) {
        when (nameFragment) {
            "list" -> {
                requireFragmentManager().beginTransaction().apply {
                    replace(R.id.flFragment, ListMoviesFragment.newInstance())
                    addToBackStack(null)
                    commit()
                }
            }

            "search" -> {
                requireFragmentManager().beginTransaction().apply {
                    replace(R.id.flFragment, SearchFragment.newInstance())
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
}