package softspark.com.inventorypilot.home.presentation.ui.products.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.utils.Constants.OWNER_ROLE
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ZERO
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_ROLE_PREFERENCE
import softspark.com.inventorypilot.databinding.ItemLayoutCardProductBinding
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.presentation.ui.products.utils.ProductSelectedListener
import javax.inject.Inject

class ProductsAdapter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val inventoryPilotPreferences: InventoryPilotPreferences
) : ListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductsDiffCallback()) {

    private lateinit var productSelectedListener: ProductSelectedListener

    class ProductViewHolder(
        private val itemBinding: ItemLayoutCardProductBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            context: Context,
            productSection: Product,
            inventoryPilotPreferences: InventoryPilotPreferences,
            productSelected: (Product, Boolean) -> Unit
        ) {
            with(itemBinding) {
                if (productSection.stock <= VALUE_ZERO) {
                    addCartButton.visibility = View.GONE
                }

                if (inventoryPilotPreferences.getValuesString(USER_ROLE_PREFERENCE) == OWNER_ROLE) {
                    editProductIv.visibility = View.VISIBLE
                }

                titleProductTv.text = productSection.name

                descriptionProductTv.text = productSection.description

                priceProductTv.text = String.format(
                    context.getString(R.string.text_price_product),
                    productSection.price.toString()
                )

                stockProductTv.text = String.format(
                    context.getString(R.string.text_quantity_product),
                    productSection.stock.toString()
                )

                addCartButton.setOnClickListener {
                    productSelected(productSection, false)
                }

                editProductIv.setOnClickListener {
                    productSelected(productSection, true)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val productCardBinding = ItemLayoutCardProductBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return ProductViewHolder(productCardBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(
            context = context,
            productSection = product,
            inventoryPilotPreferences = inventoryPilotPreferences
        ) { productSelected, isEditOption ->
            if (!isEditOption) {
                productSelectedListener.addToCartProductSelected(productSelected, position)
            } else {
                productSelectedListener.editProductSelected(
                    productId = productSelected.id,
                    position
                )
            }
        }
    }

    fun initListeners(listener: ProductSelectedListener) {
        productSelectedListener = listener
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
