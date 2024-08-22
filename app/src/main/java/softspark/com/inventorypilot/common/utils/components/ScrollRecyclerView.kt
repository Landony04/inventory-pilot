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
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

            if ((visibleItemCount + pastVisibleItems) > totalItemCount) {
                listener()
            }
        }
    }
}

interface ScrollRecyclerViewListener {
    operator fun invoke()
}