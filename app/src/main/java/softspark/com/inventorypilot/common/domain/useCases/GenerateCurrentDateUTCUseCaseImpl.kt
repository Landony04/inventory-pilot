package softspark.com.inventorypilot.common.domain.useCases

import android.os.Build
import androidx.annotation.RequiresApi
import softspark.com.inventorypilot.common.utils.Constants
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class GenerateCurrentDateUTCUseCaseImpl : GenerateCurrentDateUTCUseCase {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun invoke(): String {
        val currentDateTime = ZonedDateTime.now(ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern(Constants.UTC_DATE_FORMAT)
        return currentDateTime.format(formatter)
    }
}
