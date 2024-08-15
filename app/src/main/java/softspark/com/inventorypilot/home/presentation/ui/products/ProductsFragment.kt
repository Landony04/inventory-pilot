package softspark.com.inventorypilot.home.presentation.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.databinding.FragmentProductsBinding
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val productCategoryViewModel: ProductCategoryViewModel by viewModels()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding
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

        setUpObservers()
        getCategories()
    }

    private fun getCategories() {
        productCategoryViewModel.getProductCategories()
    }

    private fun handleGetProductCategory(result: Result<ArrayList<ProductCategory>>) {
        when (result) {
            is Result.Error -> println("Tenemos este error: ${result.exception.message}")
            is Result.Success -> println("Tenemos el listado")
            Result.Loading -> println("Tenemos que mostrar el loading")
        }
    }

    private fun setUpObservers() {
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