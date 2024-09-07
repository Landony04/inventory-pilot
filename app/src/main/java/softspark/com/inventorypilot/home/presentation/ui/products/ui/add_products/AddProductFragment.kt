package softspark.com.inventorypilot.home.presentation.ui.products.ui.add_products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.presentation.products.SpinnerAdapter
import softspark.com.inventorypilot.common.utils.Constants
import softspark.com.inventorypilot.common.utils.Constants.EMPTY_STRING
import softspark.com.inventorypilot.common.utils.Constants.PRODUCT_PARCELABLE_REQUEST_KEY
import softspark.com.inventorypilot.common.utils.Constants.PRODUCT_PARCELABLE_RESULT_KEY
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ZERO
import softspark.com.inventorypilot.common.utils.components.ItemSelectedFromSpinnerListener
import softspark.com.inventorypilot.common.utils.components.ItemSelectedSpinner
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.databinding.FragmentAddProductBinding
import softspark.com.inventorypilot.home.domain.entities.AddProductResult
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.presentation.ui.products.viewModel.AddProductViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AddProductFragment : Fragment(), ItemSelectedFromSpinnerListener {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding

    private val args: AddProductFragmentArgs by navArgs()

    private val addProductViewModel: AddProductViewModel by viewModels()

    private var userInteraction = false
    private var productCategoryIdCurrent = EMPTY_STRING
    private var addDateProduct = EMPTY_STRING
    private var createByProduct = EMPTY_STRING

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        getInitialData()
        initListeners()
        setUpActionBar()
        setUpObservers()
    }

    private fun getArgs() {
        args.productId?.let { productId ->
            getProductById(productId)
        }
    }

    private fun addProduct() {
        showAndHideButtonSave(false)
        addProductViewModel.addOrUpdateProduct(
            productCategoryIdCurrent,
            binding?.nameProductTie?.text?.toString() ?: EMPTY_STRING,
            binding?.descriptionProductTie?.text?.toString() ?: EMPTY_STRING,
            binding?.stockProductTie?.text?.toString() ?: EMPTY_STRING,
            binding?.priceProductTie?.text?.toString() ?: EMPTY_STRING,
            args.productId,
            args.productId != null
        )
    }

    private fun getProductById(productId: String) {
        addProductViewModel.getProductById(productId)
    }

    private fun getInitialData() {
        addProductViewModel.getProductCategories()
    }

    private fun handleGetProductCategory(result: Result<ArrayList<ProductCategory>>) {
        when (result) {
            is Result.Error -> {
                binding?.spinnerProgressBar?.visibility = View.GONE
            }

            is Result.Success -> {
                binding?.spinnerProgressBar?.visibility = View.GONE
                binding?.categorySpinner?.visibility = View.VISIBLE
                initAdapterSpinner(result.data)
            }

            Result.Loading -> binding?.spinnerProgressBar?.visibility = View.VISIBLE
        }
    }

    private fun handleValidateProduct(result: AddProductResult) {
        when (result) {
            is AddProductResult.Invalid -> {
                showAndHideButtonSave(true)
                showToast(result.errorMessage)
            }

            AddProductResult.Valid -> {
                showAndHideButtonSave(true)
                showToast(
                    if (args.productId != null) getString(R.string.text_modified_product_successfully) else getString(
                        R.string.text_add_product_successfully
                    )
                )
                args.productId?.let {
                    sendResultBack()
                }

                findNavController().navigateUp()
            }
        }
    }

    private fun sendResultBack() {
        val bundle = Bundle()
        val product = Product(
            args.productId ?: EMPTY_STRING,
            productCategoryIdCurrent,
            binding?.nameProductTie?.text?.toString() ?: EMPTY_STRING,
            binding?.descriptionProductTie?.text?.toString() ?: EMPTY_STRING,
            binding?.priceProductTie?.text?.toString()?.toDouble() ?: 0.0,
            binding?.stockProductTie?.text?.toString()?.toInt() ?: VALUE_ZERO,
            if (args.productId != null) addDateProduct else addProductViewModel.getCurrentDateUtc(),
            if (args.productId != null) createByProduct else addProductViewModel.getUserId()
        )

        bundle.putParcelable(PRODUCT_PARCELABLE_RESULT_KEY, product)
        parentFragmentManager.setFragmentResult(PRODUCT_PARCELABLE_REQUEST_KEY, bundle)
    }

    private fun handleGetProductById(result: Result<Product>) {
        when (result) {
            is Result.Error -> {
                showToast(getString(R.string.text_error_get_product))
                findNavController().navigateUp()
            }

            is Result.Success -> {
                with(result.data) {
                    binding?.nameProductTie?.setText(this.name)
                    binding?.descriptionProductTie?.setText(this.description)
                    binding?.stockProductTie?.setText("${this.stock}")
                    binding?.priceProductTie?.setText("${this.price}")
                    productCategoryIdCurrent = this.categoryId
                    addDateProduct = this.addDate
                    createByProduct = this.createBy
                }
            }

            Result.Loading -> println("Mostrar progress")
        }
    }

    private fun initAdapterSpinner(categories: ArrayList<ProductCategory>) {
        val allCategories =
            listOf(
                ProductCategory(
                    Constants.VALUE_ZERO.toString(),
                    getString(R.string.title_first_option_spinner)
                )
            ) + categories

        val adapter = SpinnerAdapter(requireContext(), ArrayList(allCategories))
        binding?.categorySpinner?.adapter = adapter

        if (args.productId != null) {
            val position =
                allCategories.indexOfFirst { productCategory -> productCategory.id == productCategoryIdCurrent }
            if (position >= VALUE_ZERO) {
                binding?.categorySpinner?.setSelection(position)
            }
        }
    }

    private fun initListeners() {
        binding?.categorySpinner?.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                userInteraction = true
                view.performClick()
            }
            false
        }

        binding?.categorySpinner?.onItemSelectedListener = ItemSelectedSpinner(this)

        binding?.saveBtn?.setOnClickListener {
            addProduct()
        }
    }

    private fun setUpActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_action_bar_add_product)
    }

    private fun setUpObservers() {
        addProductViewModel.productCategoryData.observe(
            viewLifecycleOwner,
            ::handleGetProductCategory
        )

        addProductViewModel.validateProductData.observe(viewLifecycleOwner, ::handleValidateProduct)

        addProductViewModel.productData.observe(viewLifecycleOwner, ::handleGetProductById)
    }

    private fun showAndHideButtonSave(isVisible: Boolean) {
        binding?.saveBtn?.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        binding?.saveProgressBar?.visibility = if (!isVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(requireContext(), message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addProductViewModel.productCategoryData.removeObservers(viewLifecycleOwner)
        addProductViewModel.productData.removeObservers(viewLifecycleOwner)
        _binding = null
    }

    override fun itemSelected(parent: AdapterView<*>?, position: Int) {
        if (!userInteraction) {
            return
        }

        userInteraction = false

        if (position > Constants.VALUE_ZERO) {
            val selectedCategory = parent?.getItemAtPosition(position) as ProductCategory
            productCategoryIdCurrent = selectedCategory.id
        }
    }
}