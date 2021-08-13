package com.example.moviewflixnew.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.details.DetailMovieViewModel
import com.example.moviewflixnew.ui.utils.preferences.ManagmentPreferences
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val loginViewModel by viewModels<LoginViewModel> {viewModelFactory}

    private lateinit var setUserInfo: ManagmentPreferences
    private lateinit var navController: NavController
    private lateinit var tv_alerta_login:AppCompatTextView
    private lateinit var btnEntrar: AppCompatButton
    private lateinit var btnCadastrar: AppCompatButton
    private lateinit var email: AppCompatEditText
    private lateinit var senha: AppCompatEditText
    private lateinit var forgetPassword: AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val activity = activity as Context
        backPressed()
        initView(view,activity)
        verificaUsuarioLogado(activity)
        eventClick(activity)
    }

    private fun backPressed() {
        requireActivity().onBackPressed()
    }

    private fun eventClick(context: Context) {
        btnEntrar.setOnClickListener {
            val email = email.text.toString()
            val password = senha.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                tv_alerta_login.visibility = View.VISIBLE
                tv_alerta_login.text = getString(R.string.msg_campos_vazios)
            } else {
                initViewModel(email,password,context)
            }
        }

      btnCadastrar.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_cadastrarFragment)
        }
    }

    private fun initViewModel(email: String, password: String, context: Context) {
        loginViewModel.init(
            LoginFireBaseModel(
            email,
            password,
                ""
            )
        )
        loginViewModel.loginActionView.observe(viewLifecycleOwner, { state ->
            when(state){
                is LoginActionView.LoginSuccess -> {
                    iniInfoViewModel(email,context)
                }

                is LoginActionView.LoginError -> {
                    Toast.makeText(context,state.error,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun iniInfoViewModel(email: String, context: Context) {
        loginViewModel.initInfo(email,context)
        loginViewModel.loginInfoActionView.observe(viewLifecycleOwner, { state ->
            when(state){
                is LoginInfoActionView.LoginInfoSuccess -> {
                    if (state.success?.email != null && state.success.name != null){
                        navController.navigate(R.id.action_loginFragment_to_mainFragment)
                        setUserInfo.initializeSession(state.success.email, state.success.name)
                    }else{
                        navController.navigate(R.id.action_loginFragment_to_mainFragment)
                    }
                }

                is LoginInfoActionView.LoginInfoError -> {

                }
            }
        })
    }

    private fun initView(view:View, context: Context) {
        btnEntrar = view.findViewById(R.id.btn_entrar_login)
        btnCadastrar = view.findViewById(R.id.btn_cadastrar_login)
        tv_alerta_login = view.findViewById(R.id.tv_alerta_login)
        email = view.findViewById(R.id.edt_email_login)
        senha = view.findViewById(R.id.edt_senha_login)
        setUserInfo = ManagmentPreferences(context)
        forgetPassword = view.findViewById(R.id.forget_password)
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

    private fun verificaUsuarioLogado(context: Context){
        val usuarioLogado = FirebaseAuth.getInstance().currentUser

        if (usuarioLogado != null){
            navController.navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

}