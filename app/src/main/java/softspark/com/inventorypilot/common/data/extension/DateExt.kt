package softspark.com.inventorypilot.common.data.extension

import softspark.com.inventorypilot.common.utils.Constants.EMPTY_STRING
import softspark.com.inventorypilot.common.utils.Constants.FRIENDLY_DATE_FORMAT
import softspark.com.inventorypilot.common.utils.Constants.UTC_DATE_FORMAT
import softspark.com.inventorypilot.common.utils.Constants.UTC_FORMAT_ID
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.formatUtcToReadableDate(): String {
    val inputFormat = SimpleDateFormat(UTC_DATE_FORMAT, Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone(UTC_FORMAT_ID)

    val date = inputFormat.parse(this)

    val outputFormat = SimpleDateFormat(FRIENDLY_DATE_FORMAT, Locale.getDefault())
    outputFormat.timeZone = TimeZone.getDefault()

    return date?.let { outputFormat.format(it) } ?: EMPTY_STRING
}