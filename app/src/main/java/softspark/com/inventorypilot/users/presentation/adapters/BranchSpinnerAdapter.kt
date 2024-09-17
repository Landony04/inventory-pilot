package softspark.com.inventorypilot.users.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ZERO
import softspark.com.inventorypilot.login.domain.models.Branch

class BranchSpinnerAdapter(
    private val context: Context,
    items: ArrayList<Branch>
) : ArrayAdapter<Branch>(context, VALUE_ZERO, items) {

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
        textView.text = category?.name ?: "Selecciona una sucursal"
        return view
    }

}
