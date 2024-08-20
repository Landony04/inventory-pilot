package softspark.com.inventorypilot.home.presentation.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.databinding.FragmentProductsBinding
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val productCategoryViewModel: ProductViewModel by viewModels()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var productsAdapter: ProductsAdapter

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

        setUpActionBar()
        setUpObservers()
        getInitData()
        initAdapter()
        initListeners()
    }

    private fun getInitData() {
        getProducts()
        productCategoryViewModel.getProductCategories()
    }

    private fun getProducts() {
        productCategoryViewModel.getAllProducts()
    }

    private fun handleGetAllProducts(result: Result<ArrayList<Product>>) {
        when (result) {
            is Result.Error -> {
                binding?.progressBarBottom?.visibility = View.GONE
            }

            is Result.Success -> {
                binding?.progressBarBottom?.visibility = View.GONE
                productsAdapter.submitList(result.data)
            }

            Result.Loading -> {
                binding?.progressBarBottom?.visibility = View.VISIBLE
            }
        }
    }

    private fun handleGetProductCategory(result: Result<ArrayList<ProductCategory>>) {
        when (result) {
            is Result.Error -> println("Tenemos este error: ${result.exception.message}")
            is Result.Success -> println("Tenemos el listado")
            Result.Loading -> println("Tenemos que mostrar el loading")
        }
    }

    private fun initAdapter() {
        binding?.productsRv?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productsAdapter
        }
    }

    private fun initListeners() {
        binding?.productsRv?.addOnScrollListener(onScrollCallback)
    }

    private val onScrollCallback = (object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (!recyclerView.canScrollVertically(1)) {
                getProducts()
            }
        }
    })

    private fun setUpActionBar() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        productCategoryViewModel.productCategoryData.removeObservers(viewLifecycleOwner)
        _binding = null
    }
}