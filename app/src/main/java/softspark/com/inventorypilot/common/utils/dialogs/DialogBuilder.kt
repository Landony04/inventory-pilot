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
}