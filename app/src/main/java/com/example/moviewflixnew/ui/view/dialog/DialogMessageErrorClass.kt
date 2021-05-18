package com.example.moviewflixnew.ui.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class DialogMessageErrorClass(private var message:String):DialogFragment() {

    companion object{
        fun newInstance(message: String) = DialogMessageErrorClass(message)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Ops!")
                .setMessage(message)
                .setNegativeButton("Tentar Novamente",DialogInterface.OnClickListener { dialog, id ->
                    dismiss()
                })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")


    }

}