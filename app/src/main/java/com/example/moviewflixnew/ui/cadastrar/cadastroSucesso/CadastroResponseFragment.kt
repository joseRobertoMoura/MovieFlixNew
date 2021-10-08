package com.example.moviewflixnew.ui.cadastrar.cadastroSucesso

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.cadastrar.Consts
import com.example.moviewflixnew.utils.ColorBars
import kotlin.properties.Delegates

class CadastroResponseFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var message:String
    private val colorBars = ColorBars()
    private var type by Delegates.notNull<Boolean>()
    private var imageId by Delegates.notNull<Int>()
    private lateinit var image:AppCompatImageView
    private lateinit var textMessage: AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cadastro_response, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        this.arguments?.getString(Consts.MESSAGE.toString()).let {
            if (it != null){
                message = it
            }
        }

        this.arguments?.getBoolean(Consts.RESPONSE.toString()).let {
            if (it != null){
                type = it
            }
        }

        this.arguments?.getInt(Consts.ID.toString()).let {
            if (it != null){
                imageId = it
            }
        }

        setViews(view)
        checkResponse()
    }

    private fun checkResponse() {
        textMessage.text = message
        image.load(imageId)
        if (type){
            R.id.action_cadastroResponseFragment_to_loginFragment.changeFragment(TIME_SUCCESS)
        }else if (!type){
            R.id.action_cadastroResponseFragment_to_cadastrarFragment.changeFragment(TIME_ERRO)
        }
    }

    private fun Int.changeFragment(time:Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(this)
            colorBars.changeColorPrimary(requireActivity())
        },time)
    }

    private fun setViews(view: View) {
        image = view.findViewById(R.id.icon_result_cadastro)
        textMessage = view.findViewById(R.id.text_result_cadastro)
    }

    companion object {
        fun newInstance() = CadastroResponseFragment()
        const val TIME_SUCCESS = 2000L
        const val TIME_ERRO = 4000L
    }
}