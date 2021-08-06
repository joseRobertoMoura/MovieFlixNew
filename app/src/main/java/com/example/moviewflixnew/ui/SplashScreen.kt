package com.example.moviewflixnew.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R

class SplashScreen : Fragment() {

    lateinit var navController: NavController


    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).mainComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backPressed()
        navController = Navigation.findNavController(view)
        Handler(Looper.getMainLooper()).postDelayed({
            navController!!.navigate(R.id.action_splashScreen_to_loginFragment)
        },2000)
    }

    private fun backPressed() {
        requireActivity().onBackPressed()
    }

    companion object {
        fun newInstance() = SplashScreen()
    }
}