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

                bindNameProduct(productSection.name)
                bindDescriptionProduct(productSection.description)
                bindPriceProduct(productSection.price, context)
                bindStockProduct(productSection.stock, context)

                addCartButton.setOnClickListener {
                    productSelected(productSection, false)
                }

                editProductIv.setOnClickListener {
                    productSelected(productSection, true)
                }
            }
        }

        fun bindNameProduct(name: String) {
            itemBinding.titleProductTv.text = name
        }

        fun bindDescriptionProduct(description: String) {
            itemBinding.descriptionProductTv.text = description
        }

        fun bindPriceProduct(price: Double, context: Context) {
            itemBinding.priceProductTv.text = String.format(
                context.getString(R.string.text_price_product),
                price.toString()
            )
        }

        fun bindStockProduct(stock: Int, context: Context) {
            itemBinding.stockProductTv.text = String.format(
                context.getString(R.string.text_quantity_product),
                stock.toString()
            )
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

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (val latestPayload = payloads.lastOrNull()) {
            is ArticleChangePayload.Name -> holder.bindNameProduct(latestPayload.newName)
            is ArticleChangePayload.Description -> holder.bindDescriptionProduct(latestPayload.newDescription)
            is ArticleChangePayload.Price -> holder.bindPriceProduct(
                latestPayload.newPrice,
                context
            )

            is ArticleChangePayload.Stock -> holder.bindStockProduct(
                latestPayload.newStock,
                context
            )

            else -> onBindViewHolder(holder, position)
        }
    }

    fun initListeners(listener: ProductSelectedListener) {
        productSelectedListener = listener
    }
}


private sealed interface ArticleChangePayload {

    data class Name(val newName: String) : ArticleChangePayload

    data class Description(val newDescription: String) : ArticleChangePayload

    data class Stock(val newStock: Int) : ArticleChangePayload

    data class Price(val newPrice: Double) : ArticleChangePayload
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

    override fun getChangePayload(oldItem: Product, newItem: Product): Any? {
        return when {
            oldItem.name != newItem.name -> {
                ArticleChangePayload.Name(newItem.name)
            }

            oldItem.description != newItem.description -> {
                ArticleChangePayload.Description(newItem.description)
            }

            oldItem.stock != newItem.stock -> {
                ArticleChangePayload.Stock(newItem.stock)
            }

            oldItem.price != newItem.price -> {
                ArticleChangePayload.Price(newItem.price)
            }

            else -> super.getChangePayload(oldItem, newItem)
        }
    }
}
