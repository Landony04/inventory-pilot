package softspark.com.inventorypilot.common.utils.dialogs

import android.content.Context

interface DialogBuilder {
    fun showDatePickerDialog(
        context: Context,
        selectedDates: (selectedDate: String, selectedDateUTC: String) -> Unit
    )

    fun showToast(
        context: Context,
        message: String
    )

    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String,
        textNegative: String? = null,
        onAcceptedClicked: (() -> Unit)? = null,
        onCancelClicked: (() -> Unit)? = null
    )
}