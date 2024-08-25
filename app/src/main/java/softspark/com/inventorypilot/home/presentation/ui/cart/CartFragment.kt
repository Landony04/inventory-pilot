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
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ZERO
import softspark.com.inventorypilot.databinding.FragmentCartBinding
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding

    val cartViewModel: CartViewModel by viewModels()

    @Inject
    lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCart()
        initAdapter()
        initListeners()
        setUpActionBar()
        setUpObservers()
    }

    private fun emptyCart() {
        cartViewModel.emptyCart()
    }

    private fun getCart() {
        cartViewModel.getCart()
    }

    private fun handleAddProductToCart(result: Result<Boolean>) {
        when (result) {
            is Result.Error -> TODO()
            is Result.Success -> TODO()
            Result.Loading -> TODO()
        }
    }

    private fun handleEmptyCart(result: Result<Boolean>) {
        when (result) {
            is Result.Error -> {
                binding?.cartPb?.visibility = View.GONE
                println("Error al obtener el carro")
            }

            is Result.Success -> {
                binding?.cartPb?.visibility = View.GONE
                getCart()
            }

            Result.Loading -> binding?.cartPb?.visibility = View.VISIBLE
        }
    }

    private fun handleGetCart(result: Result<ArrayList<CartItem>>) {
        when (result) {
            is Result.Error -> {
                binding?.cartPb?.visibility = View.GONE
                println("Error al obtener el carro")
            }

            is Result.Success -> {
                binding?.cartPb?.visibility = View.GONE

                if (result.data.size > VALUE_ZERO) {
                    binding?.cartRv?.visibility = View.VISIBLE
                    binding?.buttonsContainer?.visibility = View.VISIBLE
                    cartAdapter.submitList(result.data)
                } else {
                    binding?.cartRv?.visibility = View.GONE
                    binding?.buttonsContainer?.visibility = View.GONE
                    cartAdapter.submitList(arrayListOf())
                }
            }

            Result.Loading -> binding?.cartPb?.visibility = View.VISIBLE
        }
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
            emptyCart()
        }

        binding?.finishSaleButton?.setOnClickListener { }
    }

    private fun setUpActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_action_bar_cart)
    }

    private fun setUpObservers() {
        cartViewModel.addProductToCartData.observe(viewLifecycleOwner, ::handleAddProductToCart)
        cartViewModel.emptyCartData.observe(viewLifecycleOwner, ::handleEmptyCart)
        cartViewModel.getCartData.observe(viewLifecycleOwner, ::handleGetCart)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cartViewModel.addProductToCartData.removeObservers(viewLifecycleOwner)
        cartViewModel.emptyCartData.removeObservers(viewLifecycleOwner)
        cartViewModel.getCartData.removeObservers(viewLifecycleOwner)
        _binding = null
    }
}