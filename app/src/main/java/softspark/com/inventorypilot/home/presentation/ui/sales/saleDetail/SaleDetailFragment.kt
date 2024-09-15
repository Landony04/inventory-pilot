package softspark.com.inventorypilot.home.presentation.ui.sales.saleDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.components.MenuProviderUtils
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.databinding.FragmentSaleDetailBinding
import softspark.com.inventorypilot.home.domain.models.sales.SaleDetail
import softspark.com.inventorypilot.home.presentation.ui.sales.saleDetail.adapters.ProductSaleDetailAdapter
import softspark.com.inventorypilot.home.presentation.ui.sales.saleDetail.viewModel.SaleDetailViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SaleDetailFragment : Fragment() {

    private var _binding: FragmentSaleDetailBinding? = null
    private val binding get() = _binding

    private val args: SaleDetailFragmentArgs by navArgs()

    private val saleDetailViewModel: SaleDetailViewModel by viewModels()

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    @Inject
    lateinit var productSaleDetailAdapter: ProductSaleDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSaleDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideActionBarOptions()
        initAdapter()
        setUpActionBar()
        setUpObservers()
        getSaleDetail()
    }

    private fun getSaleDetail() {
        saleDetailViewModel.getSaleById(args.saleId)
    }

    private fun handleGetSaleDetail(result: Result<SaleDetail>) {
        when (result) {
            is Result.Error -> {
                showToast("Ocurrió un error al obtener la venta - ${result.exception.message}")
                println("Error: ${result.exception.message}")
                findNavController().navigateUp()
            }

            is Result.Success -> {
                setData(result.data)
            }

            Result.Loading -> println("Mostrar progress")
        }
    }

    private fun hideActionBarOptions() {
        // Obtener el MenuHost (generalmente es la Activity)
        val menuHost: MenuHost = requireActivity()

        // Agregar un MenuProvider para gestionar el menú
        menuHost.addMenuProvider(MenuProviderUtils(), viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initAdapter() {
        binding?.productsSaleRv?.apply {
//            isNestedScrollingEnabled = false
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productSaleDetailAdapter
        }
    }

    private fun setData(sale: SaleDetail) {
        binding?.statusSaleTv?.text = sale.statusWithFormat
        binding?.dateSaleTv?.text = sale.dateWithFormat
        binding?.totalPriceTv?.text =
            String.format(getString(R.string.text_total_amount_sale), sale.totalAmount)
        binding?.saleByTv?.text = sale.userNameWithFormat
        productSaleDetailAdapter.submitList(sale.products)
    }

    private fun setUpActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.text_title_sale_detail)
    }

    private fun setUpObservers() {
        saleDetailViewModel.saleData.observe(viewLifecycleOwner, ::handleGetSaleDetail)
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(requireContext(), message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saleDetailViewModel.saleData.removeObservers(viewLifecycleOwner)
        _binding = null
    }
}