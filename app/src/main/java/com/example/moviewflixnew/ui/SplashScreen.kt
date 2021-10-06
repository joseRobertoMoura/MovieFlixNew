package com.example.moviewflixnew.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.example.moviewflixnew.utils.ColorBars

class SplashScreen : Fragment() {

    private lateinit var navController: NavController
    private lateinit var window:Window
    private val colorBars = ColorBars()

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
        colorBars.changeColorPrimary(requireActivity())
        navController = Navigation.findNavController(view)
        Handler(Looper.getMainLooper()).postDelayed({
            navController!!.navigate(R.id.action_splashScreen_to_loginFragment)
            colorBars.changeColorPrimary(requireActivity())
        },2000)
    }

    private fun backPressed() {
        requireActivity().onBackPressed()
    }

    companion object {
        fun newInstance() = SplashScreen()
    }
}