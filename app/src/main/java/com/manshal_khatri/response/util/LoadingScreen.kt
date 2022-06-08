package com.manshal_khatri.response.util

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatDialog
import com.manshal_khatri.response.R

class LoadingScreen(context: Context) : AppCompatDialog(context) {

    fun createLoadingDialog() : Dialog{
       val dialog = Dialog(context)
        dialog.setContentView(R.layout.loading_screen)
        return dialog
    }
    fun toggleDialog(dialog: Dialog){
        if(dialog.isShowing){
            dialog.hide()
        }else{
            dialog.show()
        }
    }

}