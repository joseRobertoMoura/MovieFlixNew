package com.example.moviewflixnew.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.search.SearchFragment
import com.example.moviewflixnew.ui.listMovies.ListMoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private var numPage:Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private fun backPressed() {
        requireActivity().onBackPressed()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backPressed()
        fragments("list")
        navController = Navigation.findNavController(view)
        view.findViewById<ImageView>(R.id.back_pag).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.next_pag).setOnClickListener(this)
        val bottomBar = view.findViewById<BottomNavigationView>(R.id.bottom_bar)
        bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    logout()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.list -> {
                    fragments("list")
                    setVisibilityBtnPag(true)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.search -> {
                    fragments("search")
                    setVisibilityBtnPag(false)
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
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, ListMoviesFragment.newInstance(numPage.toString()))
                    addToBackStack(null)
                    commit()
                }
            }

            "search" -> {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, SearchFragment.newInstance())
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.next_pag -> {
                    numPage += 1
                    fragments("list")
            }
            R.id.back_pag -> {
                if (numPage > 1){
                    numPage -= 1
                    fragments("list")
                }
            }
        }
    }

    private fun setVisibilityBtnPag(visible:Boolean){
        if (visible){
            back_pag.visibility = View.VISIBLE
            next_pag.visibility = View.VISIBLE
        }else{
            back_pag.visibility = View.GONE
            next_pag.visibility = View.GONE
        }

    }
}