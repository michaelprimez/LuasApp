package com.michaelkeskinidis.luasapp.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.michaelkeskinidis.luasapp.R

class NoInternetConnectionDialog : DialogFragment() {

    private lateinit var updateParent: DialogCallback

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity?.layoutInflater
        val builder = AlertDialog.Builder(activity as Context)
        val rootView = inflater?.inflate(R.layout.dialog_no_internet_connection, null)

        val buttonOK = rootView?.findViewById<Button>(R.id.btnOKNoInternetConnectionDialog)

        buttonOK?.setOnClickListener {
            updateParent.onOK()
            dismiss()
        }

        builder.setView(rootView)
        return builder.create()
    }

    companion object {
        fun newInstance(onDialogUpdateParent: DialogCallback): NoInternetConnectionDialog {
            val dialog = NoInternetConnectionDialog()
            dialog.isCancelable = false
            val args = Bundle()
            dialog.updateParent = onDialogUpdateParent
            dialog.arguments = args
            return dialog
        }
    }
}