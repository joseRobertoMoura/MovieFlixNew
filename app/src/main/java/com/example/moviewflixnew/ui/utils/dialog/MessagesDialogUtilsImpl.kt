package com.example.moviewflixnew.ui.utils.dialog

import android.content.Context
import javax.inject.Inject

open class MessagesDialogUtilsImpl @Inject constructor() : MessagesDialogUtils {

    override fun getStringMsgError(context: Context, stringCode:Int) =
        context.getString(stringCode)

}