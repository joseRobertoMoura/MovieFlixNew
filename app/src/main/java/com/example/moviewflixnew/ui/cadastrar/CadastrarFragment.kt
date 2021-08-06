package com.example.moviewflixnew.ui.cadastrar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.MainActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastrarFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var tvAlertaCadastro:AppCompatTextView
    private lateinit var btnBack: AppCompatImageView
    private lateinit var btnCadastrar: AppCompatButton
    private lateinit var email: AppCompatEditText
    private lateinit var senha: AppCompatEditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cadastrar, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val activity = activity as Context
        initView(view)
        eventClick(activity)
    }

    private fun eventClick(context: Context) {
        btnCadastrar.setOnClickListener {
            val email = email.text.toString()
            val senha = senha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                tvAlertaCadastro.visibility = View.VISIBLE
                tvAlertaCadastro.text = getString(R.string.msg_campos_vazios)
            } else {
                cadastrarFireBase(email, senha, context)
            }
        }

        btnBack.setOnClickListener {
            backMain()
        }
    }

    private fun initView(view: View) {
        tvAlertaCadastro = view.findViewById(R.id.tv_alerta_cadastrar)
        btnBack = view.findViewById(R.id.img_back)
        btnCadastrar = view.findViewById(R.id.btn_entrar_cadastrar)
        email = view.findViewById(R.id.edt_email_cadastrar)
        senha = view.findViewById(R.id.edt_senha_cadastrar)
    }

    private fun cadastrarFireBase(email:String,senha:String,activity: Context) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(activity, "Cadastro Realizado com sucesso", Toast.LENGTH_SHORT)
                        .show()
                    backMain()
                }
            }.addOnFailureListener {
                tvAlertaCadastro.visibility = View.VISIBLE
                when (it) {
                    is FirebaseAuthWeakPasswordException -> {
                        tvAlertaCadastro.text = getString(R.string.msg_tamanho_senha)
                    }
                    is FirebaseAuthUserCollisionException -> {
                        tvAlertaCadastro.text = getString(R.string.msg_email_existente)
                    }
                    is FirebaseNetworkException -> {
                        tvAlertaCadastro.text = getString(R.string.msg_sem_conexao)
                    }
                    else -> {
                        tvAlertaCadastro.text = getString(R.string.msg_erro_cadastro_usuario)
                    }
                }
            }
    }

    private fun backMain(){
        navController.navigate(R.id.action_cadastrarFragment_to_loginFragment)
    }
}