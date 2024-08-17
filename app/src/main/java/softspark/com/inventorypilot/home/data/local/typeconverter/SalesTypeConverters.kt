package softspark.com.inventorypilot.home.data.local.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import softspark.com.inventorypilot.home.data.local.entity.products.SaleProductsList

class SalesTypeConverters {
    @TypeConverter
    fun convertSalesProductToJSONString(saleProducts: SaleProductsList): String =
        Gson().toJson(saleProducts)

    @TypeConverter
    fun convertJSONStringToProductSales(jsonString: String): SaleProductsList =
        Gson().fromJson(jsonString, SaleProductsList::class.java)
}
