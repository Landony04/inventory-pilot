package softspark.com.inventorypilot.home.presentation.ui.profits

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ONE
import softspark.com.inventorypilot.common.utils.components.ItemSelectedFromSpinnerListener
import softspark.com.inventorypilot.common.utils.components.ItemSelectedSpinner
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.databinding.FragmentProfitsBinding
import softspark.com.inventorypilot.databinding.MonthYearPickerBinding
import softspark.com.inventorypilot.home.presentation.ui.profits.adapters.ProfitAdapter
import softspark.com.inventorypilot.home.presentation.ui.profits.viewModels.ProfitsViewModel
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class ProfitsFragment : Fragment(), ItemSelectedFromSpinnerListener {

    private var _binding: FragmentProfitsBinding? = null
    private val binding get() = _binding

    private val profitsViewModel: ProfitsViewModel by viewModels()

    @Inject
    lateinit var profitAdapter: ProfitAdapter

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    private var userInteraction = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfitsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinnerAdapter()
        initObservers()
        initRecyclerView()
        initListeners()
        setUpActionBar()
    }

    private fun clearProfitsAdapter() {
        profitAdapter.submitList(emptyList())
    }

    private fun fetchDailyProfits(date: String) {
        profitsViewModel.getDailyProfits(date)
    }

    private fun fetchMonthlyProfits(month: String) {
        profitsViewModel.getMonthlyProfits(month)
    }

    private fun fillAdapter(profits: List<Pair<String, Double>>) {
        profitAdapter.submitList(profits)
    }

    private fun hideProgress() {
        binding?.pbProfits?.visibility = View.GONE
    }

    override fun itemSelected(parent: AdapterView<*>?, position: Int) {
        if (!userInteraction) {
            return
        }

        userInteraction = false

        when (position) {
            0 -> println("Limpiar listado")
            1 -> showDatePicker() // Opción "Diario"
            2 -> showMonthPicker() // Opción "Mensual"
        }
    }

    private fun initRecyclerView() {
        binding?.rvProfits?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = profitAdapter
        }
    }

    private fun initListeners() {
        binding?.spinnerTimeframe?.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                userInteraction = true
                view.performClick()
            }
            false
        }

        // Escuchar cambios en el Spinner
        binding?.spinnerTimeframe?.onItemSelectedListener = ItemSelectedSpinner(this)
    }

    private fun initObservers() {
        profitsViewModel.profits.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    hideProgress()
                    result.exception.message?.let { showToast(it) }
                }

                is Result.Loading -> showProgress()

                is Result.Success -> {
                    hideProgress()
                    clearProfitsAdapter()
                    fillAdapter(result.data.map { Pair(it.key, it.value) })
                }
            }
        }
    }

    private fun initSpinnerAdapter() {
        val options = resources.getStringArray(R.array.date_options)
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.spinnerTimeframe?.adapter = spinnerAdapter
    }

    private fun setUpActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.text_title_see_profits)
    }

    private fun showProgress() {
        binding?.pbProfits?.visibility = View.VISIBLE
    }

    private fun showDatePicker() {
        val today = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = "$year-${month + VALUE_ONE}-$dayOfMonth"
                fetchDailyProfits(selectedDate)
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showMonthPicker() {
        val today = Calendar.getInstance()
        val currentYear = today.get(Calendar.YEAR)
        val currentMonth = today.get(Calendar.MONTH)

        val dialogView =
            MonthYearPickerBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        with(dialogView) {
            // Configurar el selector de meses
            monthPicker.minValue = VALUE_ONE
            monthPicker.maxValue = 12
            monthPicker.value = currentMonth + VALUE_ONE
            monthPicker.displayedValues = resources.getStringArray(R.array.months)

            // Configurar el selector de años con restricción al año actual
            yearPicker.minValue = currentYear - 50
            yearPicker.maxValue = currentYear
            yearPicker.value = currentYear

            showDialogMonth(dialogView)
        }
    }

    private fun showDialogMonth(monthBinding: MonthYearPickerBinding) {
        // Mostrar el cuadro de diálogo
        val alert = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.text_title_select_date))
            .setView(monthBinding.root)
            .setPositiveButton(getString(R.string.text_accept_button)) { _, _ ->
                val selectedMonth = String.format(
                    getString(R.string.text_date_format),
                    monthBinding.yearPicker.value,
                    monthBinding.monthPicker.value
                )
                fetchMonthlyProfits(selectedMonth)
            }
            .setNegativeButton(getString(R.string.text_cancel_action), null)
            .create()

        alert.show()

        // Ajustar el tamaño del diálogo
        val width =
            (resources.displayMetrics.widthPixels * 0.7).toInt() // 80% del ancho de la pantalla
        alert.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(requireContext(), message)
    }
}
