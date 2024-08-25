package softspark.com.inventorypilot.home.presentation.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.databinding.ItemLayoutCardCartBinding
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import javax.inject.Inject

class CartAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    class CartViewHolder(
        private val itemBinding: ItemLayoutCardCartBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            context: Context,
            cartItemSection: CartItem,
            cartItemSelected: (CartItem) -> Unit
        ) {
            with(itemBinding) {
                quantityTv.text = "${cartItemSection.quantity}"
                totalPriceTv.text = "${cartItemSection.price}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val cartCardBinding = ItemLayoutCardCartBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return CartViewHolder(cartCardBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.bind(context = context, cartItemSection = cartItem) { cartItemSelected ->
        }
    }
}

class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(
        oldItem: CartItem,
        newItem: CartItem
    ): Boolean {
        return oldItem.cartItemId == newItem.cartItemId
    }

    override fun areContentsTheSame(
        oldItem: CartItem,
        newItem: CartItem
    ): Boolean {
        return oldItem == newItem
    }
}