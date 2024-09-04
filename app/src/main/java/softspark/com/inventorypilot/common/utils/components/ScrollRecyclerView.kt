package softspark.com.inventorypilot.common.utils.components

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import softspark.com.inventorypilot.common.utils.Constants

class ScrollRecyclerView(private val listener: ScrollRecyclerViewListener) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > Constants.VALUE_ZERO) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

            // Verificamos si hemos llegado al final y no estamos cargando
            if (lastVisibleItem >= totalItemCount - 1) {
                listener()
            }
        }
    }
}

interface ScrollRecyclerViewListener {
    operator fun invoke()
}