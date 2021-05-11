package com.example.moviewflixnew.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastrarFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var tv_alerta_cadastrar:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cadastrar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btn_entrar_cadastrar).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.img_back).setOnClickListener(this)
        tv_alerta_cadastrar = view.findViewById(R.id.tv_alerta_cadastrar)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_entrar_cadastrar -> {
                val email = view?.findViewById<EditText>(R.id.edt_email_cadastrar)?.text.toString()
                val senha = view?.findViewById<EditText>(R.id.edt_senha_cadastrar)?.text.toString()
                val activity = activity as Context

                if (email.isEmpty() || senha.isEmpty()) {
                    tv_alerta_cadastrar.text = getString(R.string.msg_campos_vazios)
                } else {
                    cadastrarFireBase(email, senha, activity)
                }
            }

            R.id.img_back -> {
                backMain()
            }
        }
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

                when (it) {
                    is FirebaseAuthWeakPasswordException -> {
                        tv_alerta_cadastrar.text = getString(R.string.msg_tamanho_senha)
                    }
                    is FirebaseAuthUserCollisionException -> {
                        tv_alerta_cadastrar.text = getString(R.string.msg_email_existente)
                    }
                    is FirebaseNetworkException -> {
                        tv_alerta_cadastrar.text = getString(R.string.msg_sem_conexao)
                    }
                    else -> {
                        tv_alerta_cadastrar.text = getString(R.string.msg_erro_cadastro_usuario)
                    }
                }
            }
    }

    private fun backMain(){
        navController.navigate(R.id.action_cadastrarFragment_to_loginFragment)
    }
}