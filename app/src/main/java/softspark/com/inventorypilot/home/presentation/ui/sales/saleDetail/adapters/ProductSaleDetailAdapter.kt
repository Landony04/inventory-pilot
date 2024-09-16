package softspark.com.inventorypilot.home.presentation.ui.sales.saleDetail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.databinding.ItemLayoutProductSaleBinding
import softspark.com.inventorypilot.home.domain.models.sales.ProductSale
import javax.inject.Inject

class ProductSaleDetailAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : ListAdapter<ProductSale, ProductSaleDetailAdapter.ProductSaleViewHolder>(
    ProductSalesDiffCallback()
) {

    class ProductSaleViewHolder(
        private val itemBinding: ItemLayoutProductSaleBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            context: Context,
            productSaleSection: ProductSale
        ) {
            with(itemBinding) {
                productQuantityTv.text = productSaleSection.quantity.toString()
                productNameTv.text = productSaleSection.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSaleViewHolder {
        val productSaleBinding = ItemLayoutProductSaleBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return ProductSaleViewHolder(productSaleBinding)
    }

    override fun onBindViewHolder(holder: ProductSaleViewHolder, position: Int) {
        val productSale = getItem(position)
        holder.bind(context = context, productSaleSection = productSale)
    }
}

class ProductSalesDiffCallback : DiffUtil.ItemCallback<ProductSale>() {
    override fun areItemsTheSame(
        oldItem: ProductSale,
        newItem: ProductSale
    ): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ProductSale,
        newItem: ProductSale
    ): Boolean {
        return oldItem == newItem
    }
}