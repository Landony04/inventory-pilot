package softspark.com.inventorypilot.home.presentation.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ONE
import softspark.com.inventorypilot.databinding.ItemLayoutCardCartBinding
import softspark.com.inventorypilot.home.domain.models.cart.CartSelectedType
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import javax.inject.Inject

class CartAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    private lateinit var cartSelectedEvents: CartSelectedEvents

    class CartViewHolder(
        private val itemBinding: ItemLayoutCardCartBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            context: Context,
            cartItemSection: CartItem,
            cartItemSelected: (CartItem) -> Unit,
            increaseSelected: () -> Unit,
            decreaseSelected: () -> Unit
        ) {
            with(itemBinding) {

                if (cartItemSection.quantity <= VALUE_ONE) {
                    decreaseQuantityTv.setCompoundDrawables(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_delete_25
                        ), null, null, null
                    )
                }

                productNameTv.text = cartItemSection.productName

                quantityTv.text = "${cartItemSection.quantity}"

                totalPriceTv.text = String.format(
                    context.getString(R.string.text_pay_for_item),
                    cartItemSection.totalPrice.toString()
                )

                increaseQuantityTv.setOnClickListener {
                    increaseSelected()
                }

                decreaseQuantityTv.setOnClickListener {
                    decreaseSelected()
                }
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
        holder.bind(context = context, cartItemSection = cartItem, {

        }, {
            cartSelectedEvents.updateQuantity(CartSelectedType.IncreaseQuantity(cartItem.cartItemId.toString()))
            updateQuantity(position, +1)
        }, {
            cartSelectedEvents.updateQuantity(CartSelectedType.DecreaseQuantity(cartItem.cartItemId.toString()))
            updateQuantity(position, -1)
        })
    }

    private fun updateQuantity(position: Int, quantity: Int) {
        val cartItem = getItem(position)
        cartItem.quantity += quantity
        notifyItemChanged(position)
    }

    fun initListener(listener: CartSelectedEvents) {
        cartSelectedEvents = listener
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