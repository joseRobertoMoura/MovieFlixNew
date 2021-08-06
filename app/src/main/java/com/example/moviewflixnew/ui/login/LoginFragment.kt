package com.example.moviewflixnew.ui.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class LoginFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var tv_alerta_login:AppCompatTextView
    private lateinit var btnEntrar: AppCompatButton
    private lateinit var btnCadastrar: AppCompatTextView
    private lateinit var email: AppCompatEditText
    private lateinit var senha: AppCompatEditText
    private lateinit var progressBar: FrameLayout

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
        initView(view)
        verificaUsuarioLogado()
        eventClick(activity)
    }

    private fun backPressed() {
        requireActivity().onBackPressed()
    }

    private fun eventClick(context: Context) {
        btnEntrar.setOnClickListener {
            val email = email.text.toString()
            val senha = senha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                tv_alerta_login.visibility = View.VISIBLE
                tv_alerta_login.text = getString(R.string.msg_campos_vazios)
            } else {
                progressBar.visibility = View.VISIBLE
                Login(email, senha, context)
            }
        }

      btnCadastrar.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_cadastrarFragment)
        }
    }

    private fun initView(view:View) {
        btnEntrar = view.findViewById(R.id.btn_entrar_login)
        btnCadastrar = view.findViewById(R.id.btn_cadastrar_login)
        tv_alerta_login = view.findViewById(R.id.tv_alerta_login)
        email = view.findViewById(R.id.edt_email_login)
        senha = view.findViewById(R.id.edt_senha_login)
        progressBar = view.findViewById(R.id.cl_progress_bar)
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

    private fun Login(email: String, senha: String, activity: Context){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(activity, "Login efetuado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(R.id.action_loginFragment_to_mainFragment)
                }
            }.addOnFailureListener {
                tv_alerta_login.visibility = View.VISIBLE
                when (it) {
                    is FirebaseAuthWeakPasswordException -> {
                        tv_alerta_login.text = getString(R.string.msg_senha_incorreta)
                    }
                    else -> {
                        tv_alerta_login.text = getString(R.string.msg_erro_login)
                    }
                }
            }
        progressBar.visibility = View.GONE
    }

    private fun verificaUsuarioLogado(){
        val usuarioLogado = FirebaseAuth.getInstance().currentUser

        if (usuarioLogado != null){
            navController.navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

}