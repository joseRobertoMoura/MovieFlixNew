package com.example.moviewflixnew.ui.utils.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.listMovies.ListMoviesFragment
import java.lang.IllegalStateException

class DialogMessageErrorClass(private var message:String):DialogFragment() {

    companion object{
        fun newInstance(message: String) = DialogMessageErrorClass(message)
        const val NUM_PAGE = "1"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Ops!")
                .setMessage(message)
                .setNegativeButton("Tentar Novamente",DialogInterface.OnClickListener{ dialog, id ->
                    parentFragmentManager.beginTransaction().apply {
                        val bundle = Bundle()
                        bundle.putString("numPage", NUM_PAGE)
                        replace(R.id.flFragment, ListMoviesFragment::class.java, bundle)
                        addToBackStack(null)
                        commit()
                    }
                   dismiss()
                })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")


    }

}