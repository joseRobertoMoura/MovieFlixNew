package com.example.moviewflixnew.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.accountInfo.AccounInfoFragment
import com.example.moviewflixnew.ui.favorites.FavoritesFragment
import com.example.moviewflixnew.ui.search.SearchFragment
import com.example.moviewflixnew.ui.listMovies.ListMoviesFragment
import com.example.moviewflixnew.ui.utils.preferences.ManagmentPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(){

    private lateinit var navController: NavController
    private var numPage:Int = 1
    private lateinit var btnMenuInfo: AppCompatImageView
    private lateinit var fragmentMain:FrameLayout
    private lateinit var topBar: ConstraintLayout
    private lateinit var setUserInfo: ManagmentPreferences

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
        val activity = activity as Context
        initView(view,activity)
        initMenuInfo(view, activity)
        eventBottomBar(view)
    }

    private fun eventBottomBar(view:View) {
        val bottomBar = view.findViewById<BottomNavigationView>(R.id.bottom_bar)
        bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.list -> {
                    fragments("list")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.search -> {
                    fragments("search")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    fragments("favoritos")
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }

    private fun initView(view: View, context: Context) {
        navController = Navigation.findNavController(view)
        fragmentMain = view.findViewById(R.id.flFragment)
        topBar = view.findViewById(R.id.topBar)
        setUserInfo = ManagmentPreferences(context)
    }

    private fun initMenuInfo(view: View,context: Context) {
        btnMenuInfo = view.findViewById(R.id.btn_menu_info)
        val popupAdmin = PopupMenu(context ,btnMenuInfo)
        popupAdmin.inflate(R.menu.menu_info)
        popupAdmin.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_logout -> {
                    logout()
                    return@setOnMenuItemClickListener true
                }
                R.id.item_account -> {
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, AccounInfoFragment.newInstance())
                        commit()
                    }
                    return@setOnMenuItemClickListener  true
                }
                else -> {
                    return@setOnMenuItemClickListener true
                }
            }
        }
        btnMenuInfo.setOnClickListener {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menuAdmin = popup.get(popupAdmin)
                menuAdmin.javaClass
                    .getDeclaredMethod("setShowIcon",Boolean::class.java)
                    .invoke(menuAdmin,true)
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                popupAdmin.show()
            }
        }
    }

    private fun backMain() {
        navController.navigate(R.id.action_mainFragment_to_loginFragment)
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        setUserInfo.finishSession()
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

            "favoritos" -> {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, FavoritesFragment.newInstance())
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }


    companion object{
        fun newInstance() = MainFragment()
    }
}