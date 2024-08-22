package softspark.com.inventorypilot.home.presentation.ui.sales

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.databinding.ItemLayoutCardSaleBinding
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import javax.inject.Inject

class SalesAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : ListAdapter<Sale, SalesAdapter.SaleViewHolder>(SalesDiffCallback()) {

    class SaleViewHolder(
        private val itemBinding: ItemLayoutCardSaleBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            context: Context,
            saleSection: Sale,
            saleSelected: (Sale) -> Unit
        ) {
            with(itemBinding) {

                dateSaleTv.text = saleSection.date
                detailsSaleTv.text = String.format(
                    context.getString(R.string.text_details_sale),
                    "${saleSection.products.size}",
                    "${saleSection.totalAmount}"
                )

                detailsButton.setOnClickListener {
                    saleSelected(saleSection)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val saleCardBinding = ItemLayoutCardSaleBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return SaleViewHolder(saleCardBinding)
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        val sale = getItem(position)
        holder.bind(context = context, saleSection = sale) { saleSelected ->
        }
    }
}

class SalesDiffCallback : DiffUtil.ItemCallback<Sale>() {
    override fun areItemsTheSame(
        oldItem: Sale,
        newItem: Sale
    ): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Sale,
        newItem: Sale
    ): Boolean {
        return oldItem == newItem
    }
}