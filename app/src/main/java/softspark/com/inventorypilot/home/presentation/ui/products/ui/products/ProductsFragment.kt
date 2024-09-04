package softspark.com.inventorypilot.home.presentation.ui.products.ui.products

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.presentation.products.SpinnerAdapter
import softspark.com.inventorypilot.common.utils.Constants.OWNER_ROLE
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ONE
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ZERO
import softspark.com.inventorypilot.common.utils.components.ItemSelectedFromSpinnerListener
import softspark.com.inventorypilot.common.utils.components.ItemSelectedSpinner
import softspark.com.inventorypilot.common.utils.components.ScrollRecyclerView
import softspark.com.inventorypilot.common.utils.components.ScrollRecyclerViewListener
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_ROLE_PREFERENCE
import softspark.com.inventorypilot.databinding.FragmentProductsBinding
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.presentation.ui.cart.CartViewModel
import softspark.com.inventorypilot.home.presentation.ui.products.adapters.ProductsAdapter
import softspark.com.inventorypilot.home.presentation.ui.products.utils.ProductSelectedListener
import softspark.com.inventorypilot.home.presentation.ui.products.viewModel.ProductViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment(), ItemSelectedFromSpinnerListener, ScrollRecyclerViewListener,
    ProductSelectedListener {

    private val productCategoryViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    @Inject
    lateinit var preferences: InventoryPilotPreferences

    private var userInteraction = false
    private var endClearDrawable: Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        endClearDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_clear)

        setUpActionBar()
        setUpObservers()
        getInitData()
        initAdapter()
        initListeners()
    }

    private fun getInitData() {
        getProducts()
        productCategoryViewModel.getProductCategories()

        if (preferences.getValuesString(USER_ROLE_PREFERENCE) == OWNER_ROLE) {
            binding?.addProductFab?.visibility = View.VISIBLE
        }
    }

    private fun getProducts() {
        productCategoryViewModel.getAllProducts()
    }

    private fun handleGetAllProducts(result: List<Product>) {
        productsAdapter.submitList(result)
    }

    private fun handleGetProductCategory(result: Result<ArrayList<ProductCategory>>) {
        when (result) {
            is Result.Error -> {
                binding?.spinnerProgressBar?.visibility = View.GONE
            }

            is Result.Success -> {
                binding?.spinnerProgressBar?.visibility = View.GONE
                binding?.filterSpinner?.visibility = View.VISIBLE
                initAdapterSpinner(result.data)
            }

            Result.Loading -> binding?.spinnerProgressBar?.visibility = View.VISIBLE
        }
    }

    private fun initAdapter() {
        binding?.productsRv?.apply {
            setHasFixedSize(true)
            adapter = productsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun initAdapterSpinner(categories: ArrayList<ProductCategory>) {
        val allCategories =
            listOf(
                ProductCategory(
                    VALUE_ZERO.toString(),
                    getString(R.string.title_first_option_spinner)
                )
            ) + categories

        val adapter = SpinnerAdapter(requireContext(), ArrayList(allCategories))
        binding?.filterSpinner?.adapter = adapter
    }

    private fun initListeners() {
        binding?.filterSpinner?.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                userInteraction = true
                view.performClick()
            }
            false
        }

        binding?.filterSpinner?.onItemSelectedListener = ItemSelectedSpinner(this)

        binding?.productsRv?.addOnScrollListener(ScrollRecyclerView(this))

        binding?.searchEditText?.doOnTextChanged { text, _, _, _ ->
            updateSearchEditText(text)
        }

        binding?.searchIl?.setEndIconOnClickListener {
            validateIfCleanSearchInput()
        }

        productsAdapter.initListeners(this)

        binding?.addProductFab?.setOnClickListener {
            navigateToAddProduct()
        }
    }

    private fun navigateToAddProduct() {
        val action = ProductsFragmentDirections.actionFromProductToAddProduct()
        findNavController().navigate(action)
    }

    private fun setUpActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_action_bar_products)
    }

    private fun setUpObservers() {
        productCategoryViewModel.productsData.observe(viewLifecycleOwner, ::handleGetAllProducts)

        productCategoryViewModel.productCategoryData.observe(
            viewLifecycleOwner,
            ::handleGetProductCategory
        )
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(requireContext(), message)
    }

    private fun updateSearchEditText(text: CharSequence?) {
        val icSearch = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)

        binding?.searchIl?.endIconDrawable =
            if (text.isNullOrEmpty()) icSearch else endClearDrawable

        text?.let { textValue ->
            productCategoryViewModel.getProductsByName(textValue.toString())
        }
    }

    private fun resetValues() {
        productCategoryViewModel.resetValues()
    }

    private fun validateIfCleanSearchInput() {
        val endDrawable = binding?.searchIl?.endIconDrawable
        if (endDrawable != null && endDrawable.constantState == endClearDrawable?.constantState) {
            binding?.searchEditText?.text?.clear()
            resetValues()
            getProducts()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        productCategoryViewModel.productsData.removeObservers(viewLifecycleOwner)
        productCategoryViewModel.productCategoryData.removeObservers(viewLifecycleOwner)
        _binding = null
    }

    override fun itemSelected(parent: AdapterView<*>?, position: Int) {
        if (!userInteraction) {
            return
        }

        userInteraction = false

        if (position > VALUE_ZERO) {
            val selectedCategory = parent?.getItemAtPosition(position) as ProductCategory
            productCategoryViewModel.getProductsByCategoryId(selectedCategory.id)
        } else {
            resetValues()
            getProducts()
        }
    }

    override fun invoke() {
        if (!productCategoryViewModel.isLoading) {
            getProducts()
        }
    }

    override fun addToCartProductSelected(product: Product, position: Int) {
        showToast(getString(R.string.text_add_item_from_sale_success))
        cartViewModel.addProductToCart(product, VALUE_ONE)
    }
}