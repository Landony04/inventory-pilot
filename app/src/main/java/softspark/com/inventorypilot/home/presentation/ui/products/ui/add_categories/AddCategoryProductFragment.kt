package softspark.com.inventorypilot.home.presentation.ui.products.ui.add_categories

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
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.utils.Constants.EMPTY_STRING
import softspark.com.inventorypilot.common.utils.components.MenuProviderUtils
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.databinding.FragmentAddCategoryProductBinding
import softspark.com.inventorypilot.home.domain.entities.AddCategoryProductResult
import softspark.com.inventorypilot.home.presentation.ui.products.viewModel.AddCategoryProductViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AddCategoryProductFragment : Fragment() {

    private var _binding: FragmentAddCategoryProductBinding? = null
    private val binding get() = _binding

    private val addCategoryProductViewModel: AddCategoryProductViewModel by viewModels()

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCategoryProductBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideActionBarOptions()
        initListeners()
        setUpActionBar()
        setUpObservers()
    }

    private fun addCategory() {
        showAndHideProgressBarSave(true)
        addCategoryProductViewModel.addCategory(
            binding?.nameCategoryTie?.text?.toString() ?: EMPTY_STRING
        )
    }

    private fun hideActionBarOptions() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(MenuProviderUtils(), viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initListeners() {
        binding?.saveCategoryBtn?.setOnClickListener {
            addCategory()
        }
    }

    private fun handleAddCategoryResult(addCategoryProductResult: AddCategoryProductResult) {
        when (addCategoryProductResult) {
            is AddCategoryProductResult.Invalid -> {
                showAndHideProgressBarSave(false)
                showToast(addCategoryProductResult.errorMessage)
            }

            AddCategoryProductResult.Valid -> {
                showAndHideProgressBarSave(false)
                showToast(getString(R.string.text_add_category_successfully))
                findNavController().navigateUp()
            }
        }
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(requireContext(), message)
    }

    private fun showAndHideProgressBarSave(show: Boolean) {
        binding?.saveCategoryBtn?.visibility = if (show) View.INVISIBLE else View.VISIBLE
        binding?.saveCategoryPb?.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun setUpActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.text_title_add_categories)
    }

    private fun setUpObservers() {
        addCategoryProductViewModel.categoryData.observe(
            viewLifecycleOwner,
            ::handleAddCategoryResult
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addCategoryProductViewModel.categoryData.removeObservers(viewLifecycleOwner)
        _binding = null
    }
}