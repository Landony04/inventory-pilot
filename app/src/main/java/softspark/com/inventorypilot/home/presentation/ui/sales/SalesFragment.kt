package softspark.com.inventorypilot.home.presentation.ui.sales

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
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.databinding.FragmentSalesBinding
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import javax.inject.Inject

@AndroidEntryPoint
class SalesFragment : Fragment() {

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

    private fun getInitialData() {
        salesViewModel.getSalesForPage()
    }

    private fun handleGetAllSales(result: Result<ArrayList<Sale>>) {
        when (result) {
            is Result.Error -> {
                binding?.salesPb?.visibility = View.GONE
            }

            is Result.Success -> {
                binding?.salesPb?.visibility = View.GONE
                salesAdapter.submitList(result.data)
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
        binding?.dateFilterContainer?.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setInitDate() {
        binding?.dateSaleTv?.text = salesViewModel.getCurrentDate()
    }

    private fun showDatePicker() {
        dialogBuilder.showDatePickerDialog(requireContext()) { selectedDate, selectedDateUTC ->
            binding?.dateSaleTv?.text = selectedDate
            salesViewModel.getSalesByDate(selectedDateUTC)
        }
    }

    private fun setUpActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_action_bar_sales)
    }

    private fun setUpObservers() {
        salesViewModel.saleData.observe(viewLifecycleOwner, ::handleGetAllSales)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        salesViewModel.saleData.removeObservers(viewLifecycleOwner)
        _binding = null
    }
}