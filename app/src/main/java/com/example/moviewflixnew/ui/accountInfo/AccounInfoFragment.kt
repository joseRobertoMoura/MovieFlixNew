package com.example.moviewflixnew.ui.accountInfo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviewflixnew.R
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.data.model.updateUser.UpdateUserModel
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.main.MainFragment
import com.example.moviewflixnew.ui.utils.preferences.ManagmentPreferences
import javax.inject.Inject

class AccounInfoFragment : Fragment() {

    private lateinit var btnBackFragment: AppCompatImageView
    private lateinit var navController: NavController
    private lateinit var setUserInfo: ManagmentPreferences
    private lateinit var changePassword: AppCompatTextView
    private lateinit var emailUser: AppCompatTextView
    private lateinit var nameUser: AppCompatTextView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val updateViewModel by viewModels<UpdateUserViewModel> {viewModelFactory}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accoun_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val activity = activity as Context
        initView(view, activity)
        eventClick(activity)
    }

    private fun initViewModel(context: Context) {
        updateViewModel.init(UpdateUserModel(setUserInfo.getInfoUserEmail()), context)
        updateViewModel.updateUserActionView.observe(viewLifecycleOwner){ state ->
            when(state){
                is UpdateUserActionView.UpdateSuccess -> {
                    Toast.makeText(context,state.success,Toast.LENGTH_SHORT)
                        .show()
                }
                is UpdateUserActionView.UpdateError -> {
                    Toast.makeText(context,state.error,Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun eventClick(context: Context) {
        btnBackFragment.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, MainFragment.newInstance())
                commit()
            }
        }

        changePassword.setOnClickListener {
            initViewModel(context)
        }
    }

    private fun initView(view: View, context: Context) {
        btnBackFragment = view.findViewById(R.id.btn_back_account_info)
        setUserInfo = ManagmentPreferences(context)
        changePassword = view.findViewById(R.id.change_password)
        emailUser = view.findViewById(R.id.email_user_ai)
        emailUser.text = setUserInfo.getInfoUserEmail()
        nameUser = view.findViewById(R.id.name_user_ai)
        nameUser.text = setUserInfo.getInfoUserName()
    }

    companion object {
        fun newInstance() = AccounInfoFragment()
    }
}