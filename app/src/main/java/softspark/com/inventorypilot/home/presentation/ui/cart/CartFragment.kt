package softspark.com.inventorypilot.home.presentation.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ONE
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.databinding.FragmentCartBinding
import softspark.com.inventorypilot.home.domain.models.cart.CartSelectedType
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import softspark.com.inventorypilot.home.presentation.ui.sales.SalesViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(), CartSelectedEvents {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding

    private val cartViewModel: CartViewModel by viewModels()
    private val salesViewModel: SalesViewModel by viewModels()

    @Inject
    lateinit var cartAdapter: CartAdapter

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setUpObservers()
        initListeners()
        setUpActionBar()
        getCart()
    }

    private fun addSale() {
        salesViewModel.insertSale(
            cartAdapter.currentList.toList(),
            calculateTotalAmount(cartAdapter.currentList)
        )
        showToast(getString(R.string.text_sale_successfully))
    }

    private fun emptyCart() {
        cartViewModel.emptyCart()
    }

    private fun getCart() {
        cartViewModel.getCart()
    }

    private fun handleEmptyCart(result: Result<Boolean>) {
        when (result) {
            is Result.Error -> getCart()

            is Result.Success -> getCart()

            Result.Loading -> println("Mostrar algún progress")
        }
    }

    private fun handleGetCart(result: Result<ArrayList<CartItem>>) {
        when (result) {
            is Result.Error -> {
                getCart()
                showToast(getString(R.string.text_error_get_cart))
            }

            is Result.Success -> validateIfShowSale(result.data)

            Result.Loading -> println("Mostrar algun progress")
        }
    }

    private fun calculateTotalAmount(products: List<CartItem>): Double {
        return products.sumOf { it.price * it.quantity }
    }

    private fun initAdapter() {
        binding?.cartRv?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun initListeners() {
        binding?.cancelSaleButton?.setOnClickListener {
            showAlertDialog(
                getString(R.string.text_title_cancel_sale),
                getString(R.string.text_message_cancel_sale),
                getString(R.string.text_yes),
                getString(R.string.text_no)
            ) {
                emptyCart()
            }
        }

        binding?.finishSaleButton?.setOnClickListener {
            showAlertDialog(
                getString(R.string.text_title_finish_sale),
                getString(R.string.text_message_finish_sale),
                getString(R.string.text_yes),
                getString(R.string.text_no)
            ) {
                addSale()
                emptyCart()
            }
        }

        cartAdapter.initListener(this)
    }

    private fun setUpActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_action_bar_cart)
    }

    private fun setUpObservers() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        cartViewModel.emptyCartData.observe(viewLifecycleOwner, ::handleEmptyCart)

        cartViewModel.getCartData.observe(viewLifecycleOwner, ::handleGetCart)
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(requireContext(), message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cartViewModel.emptyCartData.removeObservers(viewLifecycleOwner)
        cartViewModel.getCartData.removeObservers(viewLifecycleOwner)
        _binding = null
    }

    override fun updateQuantity(cartSelectedType: CartSelectedType) = with(cartSelectedType) {
        when (this) {
            is CartSelectedType.DecreaseQuantity -> cartViewModel.decreaseQuantity(this.itemCartId)
            is CartSelectedType.IncreaseQuantity -> cartViewModel.increaseQuantity(this.itemCartId)
            is CartSelectedType.RemoveCartItem -> deleteItemInCart(this.position, this.itemCartId)
        }
    }

    private fun validateIfShowSale(cartProducts: ArrayList<CartItem>) {
        val showList = cartProducts.isNotEmpty()
        binding?.withoutCartIv?.visibility = if (!showList) View.VISIBLE else View.GONE
        binding?.cartRv?.visibility = if (showList) View.VISIBLE else View.GONE
        binding?.buttonsContainer?.visibility = if (showList) View.VISIBLE else View.GONE
        binding?.totalAmountSaleTv?.visibility = if (showList) View.VISIBLE else View.GONE

        if (showList) {
            binding?.totalAmountSaleTv?.text = String.format(
                getString(R.string.text_total_amount_sale),
                "${calculateTotalAmount(cartProducts)}"
            )
        }
        cartAdapter.submitList(cartProducts)
    }

    private fun deleteItemInCart(position: Int, itemCartId: String) {
        cartViewModel.deleteItemCart(itemCartId)
        cartAdapter.deleteItem(position)

        showToast(getString(R.string.text_delete_item_from_sale_success))

        if (cartAdapter.currentList.size <= VALUE_ONE) {
            getCart()
        }
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        positiveButton: String,
        negativeButton: String?,
        onAcceptedClicked: (() -> Unit)
    ) {
        dialogBuilder.showAlertDialog(
            requireContext(),
            title,
            message,
            positiveButton,
            negativeButton,
            { onAcceptedClicked() },
            { }
        )
    }
}