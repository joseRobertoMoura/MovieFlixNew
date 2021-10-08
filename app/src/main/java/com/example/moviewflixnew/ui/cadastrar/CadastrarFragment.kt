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
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.utils.ColorBars
import javax.inject.Inject

class CadastrarFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val cadastroViewModel by viewModels<CadastroViewModel> {viewModelFactory}
    private val colorBars = ColorBars()
    private lateinit var navController: NavController
    private lateinit var tvAlertaCadastro:AppCompatTextView
    private lateinit var btnCadastrar: AppCompatButton
    private lateinit var btnLogin: AppCompatButton
    private lateinit var email: AppCompatEditText
    private lateinit var senha: AppCompatEditText
    private lateinit var nameUser: AppCompatEditText
    private lateinit var frameBtn: ConstraintLayout


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
            val nome = nameUser.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                tvAlertaCadastro.visibility = View.VISIBLE
                tvAlertaCadastro.text = getString(R.string.msg_campos_vazios)
            } else if(verifyPassword(senha)) {
                tvAlertaCadastro.visibility = View.VISIBLE
                tvAlertaCadastro.text = getString(R.string.alert_senha)
            }else{
                btnCadastrar.isClickable = false
                initViewModel(CadastroModel(email,senha,nome), context)
            }
        }

        btnLogin.setOnClickListener {
            navController.navigate(R.id.action_cadastrarFragment_to_loginFragment)
        }

    }

    private fun verifyPassword(senha: String): Boolean {
        return senha.isEmpty()
    }

    private fun initView(view: View) {
        tvAlertaCadastro = view.findViewById(R.id.tv_alerta_cadastrar)
        frameBtn = view.findViewById(R.id.frame_cadastro)
        btnCadastrar = frameBtn.findViewById(R.id.btn_cadastrar)
        btnLogin = frameBtn.findViewById(R.id.btn_login)
        btnLogin.text = getString(R.string.text_btn_voltar_cadastro)
        email = view.findViewById(R.id.edt_email_cadastrar)
        senha = view.findViewById(R.id.edt_senha_cadastrar_first)
        nameUser = view.findViewById(R.id.name_cadastro)
    }

    private fun initViewModel(cadastroModel: CadastroModel, activity: Context) {
        cadastroViewModel.init(cadastroModel, activity)
        cadastroViewModel.cadastroActionView.observe(viewLifecycleOwner){ state ->
            when(state){
                is CadastroActionView.CadastroSuccess -> {
                    changeFragmentResponse(getString(R.string.msg_cadastro_sucesso),R.drawable.ic_ok,true)
                }
                is CadastroActionView.CadastroError -> {
                    changeFragmentResponse(
                        getString(R.string.msg_cadastro_falha),
                        R.drawable.ic_icons8_delete,
                    false)
                }
            }
        }
    }

    private fun changeFragmentResponse(message:String,id:Int, response: Boolean){
        val bundle = Bundle()
        bundle.putString(Consts.MESSAGE.toString(),message)
        bundle.putInt(Consts.ID.toString(), id)
        bundle.putBoolean(Consts.RESPONSE.toString(),response)
        navController.navigate(R.id.action_cadastrarFragment_to_cadastroResponseFragment,bundle)
        colorBars.changeColorFragmentResponse(requireActivity())
    }
}