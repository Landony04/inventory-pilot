package softspark.com.inventorypilot.home.presentation.ui.products

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.databinding.ItemLayoutCardProductBinding
import softspark.com.inventorypilot.home.domain.models.products.Product
import javax.inject.Inject

class ProductsAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : ListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductsDiffCallback()) {

    class ProductViewHolder(
        private val itemBinding: ItemLayoutCardProductBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            productSection: Product,
            productSelected: (Product) -> Unit
        ) {
            with(itemBinding) {

                titleProductTv.text = productSection.name
                descriptionProductTv.text = productSection.description

                cardContainerProduct.setOnClickListener {
                    productSelected(productSection)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductViewHolder {
        val unassociatedCardBinding = ItemLayoutCardProductBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return ProductViewHolder(unassociatedCardBinding)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductViewHolder, position: Int) {
        val card = getItem(position)
        holder.bind(productSection = card) { productSelected ->
        }
    }
}

class ProductsDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem == newItem
    }
}
