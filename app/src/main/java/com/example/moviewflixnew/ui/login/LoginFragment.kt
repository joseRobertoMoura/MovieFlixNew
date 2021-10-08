package com.example.moviewflixnew.ui.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.utils.dialog.DialogPasswordRefactor
import com.example.moviewflixnew.ui.utils.preferences.ManagmentPreferences
import com.example.moviewflixnew.utils.ColorBars
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var progressBar: ConstraintLayout
    private val colorBars = ColorBars()
    private lateinit var frameBtnLogin: ConstraintLayout

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
        backPressed()
        initView(view,requireContext())
        verificaUsuarioLogado(requireContext())
        eventClick(requireContext())
    }

    private fun backPressed() {
        requireActivity().onBackPressed()
        colorBars.changeColorPrimary(requireActivity())
    }

    private fun eventClick(context: Context) {
        btnEntrar.setOnClickListener {
            val email = email.text.toString()
            val password = senha.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                tv_alerta_login.visibility = View.VISIBLE
                tv_alerta_login.text = getString(R.string.msg_campos_vazios)
                resetFragment()
            } else {
                progressBar.visibility = View.VISIBLE
                initViewModel(email,password,context)
            }
        }

      btnCadastrar.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_cadastrarFragment)
        }

      forgetPassword.setOnClickListener {
            createDialog()
      }

    }

    private fun resetFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(R.id.action_loginFragment_self)
        },1000)
    }

    private fun createDialog() {
        val dialog = DialogPasswordRefactor.newInstance()
        dialog.show(parentFragmentManager,dialog.tag)
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
                    progressBar.visibility = View.GONE
                    Toast.makeText(context,state.error,Toast.LENGTH_SHORT).show()
                    resetFragment()
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
                        progressBar.visibility = View.GONE
                        colorBars.changeColorSecondary(requireActivity())
                    }
                }

                is LoginInfoActionView.LoginInfoError -> {
                    Toast.makeText(context,state.error,Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                    resetFragment()
                }
            }
        })
    }

    private fun initView(view:View, context: Context) {
        frameBtnLogin = view.findViewById(R.id.frame_login)
        btnEntrar = frameBtnLogin.findViewById(R.id.btn_login)
        btnCadastrar = frameBtnLogin.findViewById(R.id.btn_cadastrar)
        tv_alerta_login = view.findViewById(R.id.tv_alerta_login)
        email = view.findViewById(R.id.edt_email_login)
        senha = view.findViewById(R.id.edt_senha_login)
        setUserInfo = ManagmentPreferences(context)
        forgetPassword = view.findViewById(R.id.forget_password)
        progressBar = view.findViewById(R.id.progress_logi)
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

    private fun verificaUsuarioLogado(context: Context){
        val usuarioLogado = FirebaseAuth.getInstance().currentUser

        if (usuarioLogado != null){
            colorBars.changeColorSecondary(requireActivity())
            navController.navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

}