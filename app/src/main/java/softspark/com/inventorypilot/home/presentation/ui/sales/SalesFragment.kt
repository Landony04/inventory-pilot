package softspark.com.inventorypilot.home.presentation.ui.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants
import softspark.com.inventorypilot.common.utils.Constants.EMPTY_STRING
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.databinding.FragmentSalesBinding
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.presentation.ui.utils.SaleSelectedListener
import javax.inject.Inject

@AndroidEntryPoint
class SalesFragment : Fragment(), SaleSelectedListener {

    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding

    private val salesViewModel: SalesViewModel by viewModels()

    @Inject
    lateinit var salesAdapter: SalesAdapter

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setUpActionBar()
        setUpObservers()
        getInitialData()
        initListeners()
        setInitDate()
    }

    private fun calculateTotalAmount(products: List<Sale>): Double {
        return products.sumOf { it.totalAmount }
    }

    private fun getInitialData() {
        salesViewModel.getSalesByDate(
            salesViewModel.getCurrentDateUtc()
        )
    }

    private fun handleGetAllSales(result: Result<ArrayList<Sale>>) {
        when (result) {
            is Result.Error -> {
                binding?.salesPb?.visibility = View.GONE
                showToast(result.exception.message ?: EMPTY_STRING)
                showAndHideSalesList(arrayListOf())
            }

            is Result.Success -> {
                println("result.data ${result.data}")
                binding?.salesPb?.visibility = View.GONE
                salesAdapter.submitList(result.data)
                showAndHideSalesList(result.data)
            }

            Result.Loading -> {
                binding?.salesPb?.visibility = View.VISIBLE
            }
        }
    }

    private fun initAdapter() {
        binding?.salesRv?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = salesAdapter
        }
    }

    private fun initListeners() {
        binding?.filterContainer?.setOnClickListener {
            showDatePicker()
        }

        salesAdapter.initListeners(this)
    }

    private fun navigateToSaleDetails(saleId: String) {
        try {
            val action =
                SalesFragmentDirections.actionFromSalesToSaleDetail(saleId = saleId)
            findNavController().navigate(action)
        } catch (exception: Exception) {
            showToast(exception.message ?: Constants.EMPTY_STRING)
        }
    }

    private fun setInitDate() {
        binding?.dateSaleTv?.text = salesViewModel.getCurrentDate(Constants.DD_MMM_DATE_FORMAT)
    }

    private fun showDatePicker() {
        dialogBuilder.showDatePickerDialog(requireContext()) { selectedDate, selectedDateUTC ->
            binding?.dateSaleTv?.text = selectedDate
            salesViewModel.getSalesByDate(selectedDateUTC)
        }
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(requireContext(), message)
    }

    private fun setUpActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_action_bar_sales)
    }

    private fun setUpObservers() {
        salesViewModel.saleData.observe(viewLifecycleOwner, ::handleGetAllSales)
    }

    private fun showAndHideSalesList(sales: ArrayList<Sale>) {
        val show = sales.isNotEmpty()

        if (show) {
            binding?.totalAmountSaleTv?.text = String.format(
                getString(R.string.text_total_amount_sale),
                "${calculateTotalAmount(sales)}"
            )
        }
        binding?.totalAmountSaleTv?.visibility = if (show) View.VISIBLE else View.INVISIBLE
        binding?.salesRv?.visibility = if (show) View.VISIBLE else View.INVISIBLE
        binding?.withoutSalesIv?.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        salesViewModel.saleData.removeObservers(viewLifecycleOwner)
        _binding = null
    }

    override fun showSaleDetails(saleId: String) {
        navigateToSaleDetails(saleId = saleId)
    }
}