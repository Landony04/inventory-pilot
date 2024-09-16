package softspark.com.inventorypilot.common.utils.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.utils.Constants.EMPTY_STRING
import softspark.com.inventorypilot.databinding.AlertCommonBinding

class CreateDialogBuilder {
    fun createSimpleDialog(
        title: String?,
        content: String?,
        textPositive: String,
        textNegative: String?,
        context: Context,
        confirmCallBack: InterfaceDialog
    ): AlertDialog {
        val dialogBuilder = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = AlertCommonBinding.inflate(inflater)
        dialogView.textTitle.text = title ?: EMPTY_STRING
        if (title.isNullOrEmpty()) {
            dialogView.textTitle.visibility = View.GONE
        }
        content?.let {
            dialogView.textMessage.text = it
        }

        dialogBuilder.apply {
            setView(dialogView.root)
            setCancelable(false)
            setPositiveButton(textPositive) { dialogInterface, _ ->
                confirmCallBack.onConfirm()
                dialogInterface.cancel()
            }
        }

        textNegative?.let {
            if (it.isNotEmpty()) {
                dialogBuilder.setNegativeButton(it) { dialogInterface, _ ->
                    confirmCallBack.onCancel()
                    dialogInterface.cancel()
                }
            }
        }

        val alertDialogB = dialogBuilder.create()
        alertDialogB.show()

        return alertDialogB
    }
}

interface InterfaceDialog {
    fun onConfirm()
    fun onCancel()
}