package com.example.moviewflixnew.ui.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class LoginFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var tv_alerta_login:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btn_entrar_login).setOnClickListener(this)
        view.findViewById<TextView>(R.id.btn_cadastrar_login).setOnClickListener(this)
        tv_alerta_login = view.findViewById(R.id.tv_alerta_login)
        verificaUsuarioLogado()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_entrar_login -> {
                val email = view?.findViewById<EditText>(R.id.edt_email_login)?.text.toString()
                val senha = view?.findViewById<EditText>(R.id.edt_senha_login)?.text.toString()
                val activity = activity as Context

                if (email.isEmpty() || senha.isEmpty()) {
                    tv_alerta_login.text = getString(R.string.msg_campos_vazios)
                } else {
                    Login(email, senha, activity)
                }
            }
            R.id.btn_cadastrar_login -> {
                navController.navigate(R.id.action_loginFragment_to_cadastrarFragment)
            }
        }
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
                when (it) {
                    is FirebaseAuthWeakPasswordException -> {
                        tv_alerta_login.text = getString(R.string.msg_senha_incorreta)
                    }
                    else -> {
                        tv_alerta_login.text = getString(R.string.msg_erro_login)
                    }
                }
            }
    }

    private fun verificaUsuarioLogado(){
        val usuarioLogado = FirebaseAuth.getInstance().currentUser

        if (usuarioLogado != null){
            navController.navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

}