package softspark.com.inventorypilot.home.presentation.ui.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.databinding.FragmentSalesBinding
import softspark.com.inventorypilot.home.domain.models.sales.Sale

@AndroidEntryPoint
class SalesFragment : Fragment() {

    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding

    private val salesViewModel: SalesViewModel by viewModels()

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

        setUpObservers()
        getInitialData()
    }

    private fun getInitialData() {
        salesViewModel.getAllSales()
    }

    private fun handleGetAllSales(result: Result<ArrayList<Sale>>) {
        when (result) {
            is Result.Error -> println("Tenemos este error: ${result.exception.message}")
            is Result.Success -> println("Tenemos el listado de ventas")
            Result.Loading -> println("Tenemos que mostrar el loading")
        }
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