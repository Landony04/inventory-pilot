package softspark.com.inventorypilot.common.utils.components

import android.view.View
import android.widget.AdapterView

class ItemSelectedSpinner(
    private val itemSelected: ItemSelectedFromSpinnerListener
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        itemSelected.itemSelected(parent, position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}

interface ItemSelectedFromSpinnerListener {
    fun itemSelected(parent: AdapterView<*>?, position: Int)
}