package com.example.moviewflixnew.ui.utils.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.moviewflixnew.R
import kotlinx.android.synthetic.main.dialog_message_error.*

class DialogMessageError(private var error:String):DialogFragment(),View.OnClickListener {

    companion object {
        fun newInstance(error:String) = DialogMessageError(error)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_message_error,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog_message.text = error
        view.findViewById<Button>(R.id.btn_close_dialog).setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_close_dialog -> {
                dismiss()
            }
        }
    }
}