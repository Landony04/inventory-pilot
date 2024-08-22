package softspark.com.inventorypilot.common.presentation.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory

class SpinnerAdapter(
    private val context: Context,
    items: ArrayList<ProductCategory>
) : ArrayAdapter<ProductCategory>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item_layout)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(
            position,
            convertView,
            parent,
            R.layout.spinner_dropdown_item_layout
        )
    }

    private fun createViewFromResource(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
        layoutId: Int
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutId, parent, false)
        val category = getItem(position)

        val textView = view.findViewById<TextView>(R.id.item_text_spinner)
        textView.text = category?.name ?: "Select a category"
        return view
    }
}