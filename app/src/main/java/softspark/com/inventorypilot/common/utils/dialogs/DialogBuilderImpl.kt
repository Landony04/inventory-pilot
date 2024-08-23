package softspark.com.inventorypilot.common.utils.dialogs

import android.app.DatePickerDialog
import android.content.Context
import softspark.com.inventorypilot.common.utils.Constants.DD_MMM_DATE_FORMAT
import softspark.com.inventorypilot.common.utils.Constants.UTC_DATE_FORMAT_WITHOUT_HOURS
import softspark.com.inventorypilot.common.utils.Constants.UTC_FORMAT_ID
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class DialogBuilderImpl @Inject constructor() : DialogBuilder {
    override fun showDatePickerDialog(
        context: Context,
        selectedDates: (selectedDate: String, selectedDateUTC: String) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)

                // Formatear la fecha seleccionada a "dd MMM"
                val formattedDate =
                    SimpleDateFormat(DD_MMM_DATE_FORMAT, Locale.getDefault()).format(
                        selectedCalendar.time
                    )

                // Convertir la fecha a UTC en formato ISO 8601
                val utcDate =
                    SimpleDateFormat(UTC_DATE_FORMAT_WITHOUT_HOURS, Locale.getDefault()).apply {
                        timeZone = TimeZone.getTimeZone(UTC_FORMAT_ID)
                    }.format(selectedCalendar.time)

                selectedDates(formattedDate, utcDate)

            }, year, month, day
        )
        datePickerDialog.show()
    }
}