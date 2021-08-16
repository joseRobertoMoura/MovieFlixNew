package com.example.moviewflixnew.ui.utils.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.moviewflixnew.R
import com.example.moviewflixnew.data.model.updateUser.UpdateUserModel
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.accountInfo.UpdateUserActionView
import com.example.moviewflixnew.ui.accountInfo.UpdateUserViewModel
import javax.inject.Inject

class DialogPasswordRefactor: DialogFragment(){

    private lateinit var emailUser: AppCompatEditText
    private lateinit var btnSendEmail: AppCompatButton
    private lateinit var btnCancel: AppCompatButton
    private lateinit var txtAlert: AppCompatTextView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val updateViewModel by viewModels<UpdateUserViewModel> {viewModelFactory}

    companion object {
        fun newInstance() = DialogPasswordRefactor()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password_refactor,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        eventClick()
    }

    private fun eventClick() {

        btnSendEmail.setOnClickListener {
            val _emailUser = emailUser.text.toString()
            if (_emailUser.isEmpty()){
                txtAlert.text = getString(R.string.txt_email_alert_empity)
            }else{
                updateViewModel.init(UpdateUserModel(_emailUser),requireContext())
                updateViewModel.updateUserActionView.observe(viewLifecycleOwner,{ state ->
                    when(state){
                        is UpdateUserActionView.UpdateSuccess -> {
                            Toast.makeText(requireContext(),state.success,Toast.LENGTH_SHORT)
                                .show()
                            dismiss()
                        }

                        is UpdateUserActionView.UpdateError -> {
                            Toast.makeText(requireContext(),state.error,Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
            }
        }

        btnCancel.setOnClickListener {
            dismiss()
        }

    }

    private fun initView(view: View) {
        emailUser = view.findViewById(R.id.edt_email_passowrd_refactor)
        btnCancel = view.findViewById(R.id.btn_cancel)
        btnSendEmail = view.findViewById(R.id.btn_send_email)
        txtAlert = view.findViewById(R.id.text_alert)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

}