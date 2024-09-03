package softspark.com.inventorypilot.users.presentation.ui.addUsers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.utils.Constants
import softspark.com.inventorypilot.common.utils.Constants.EMPTY_STRING
import softspark.com.inventorypilot.common.utils.components.ItemSelectedFromSpinnerListener
import softspark.com.inventorypilot.common.utils.components.ItemSelectedSpinner
import softspark.com.inventorypilot.common.utils.components.MenuProviderUtils
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.databinding.FragmentAddUsersBinding
import softspark.com.inventorypilot.users.domain.entities.AddUserResult
import softspark.com.inventorypilot.users.presentation.viewModel.AddUserViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AddUsersFragment : Fragment(), ItemSelectedFromSpinnerListener {

    private var _binding: FragmentAddUsersBinding? = null
    private val binding get() = _binding

    private val addUserViewModel: AddUserViewModel by viewModels()

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    private var userInteraction = false
    private var roleSelectCurrent = EMPTY_STRING
    private lateinit var allRolesMap: Map<String, String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUsersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideActionBarOptions()
        initData()
        initAdapterSpinner()
        initListeners()
        setUpActionBar()
        setUpObservers()
    }

    private fun initData() {
        allRolesMap = mapOf(
            EMPTY_STRING to getString(R.string.text_all_roles),
            Constants.DISPATCHER_ROLE to getString(R.string.text_dispatcher_role),
            Constants.OWNER_ROLE to getString(R.string.text_owner_role)
        )
    }

    private fun initAdapterSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            allRolesMap.values.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.roleSpinner?.adapter = adapter
    }

    private fun hideActionBarOptions() {
        // Obtener el MenuHost (generalmente es la Activity)
        val menuHost: MenuHost = requireActivity()

        // Agregar un MenuProvider para gestionar el menú
        menuHost.addMenuProvider(MenuProviderUtils(), viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun handleAddUSer(result: AddUserResult) {
        when (result) {
            is AddUserResult.Invalid -> {
                showAndHideProgressBarSave(false)
                showToast(result.errorMessage)
            }

            AddUserResult.Valid -> {
                showAndHideProgressBarSave(false)
                showToast(getString(R.string.text_add_user_successfully))
                findNavController().navigateUp()
            }
        }
    }

    private fun initListeners() {
        binding?.roleSpinner?.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                userInteraction = true
                view.performClick()
            }
            false
        }

        binding?.saveUserBtn?.setOnClickListener {
            saveUser()
        }

        binding?.roleSpinner?.onItemSelectedListener = ItemSelectedSpinner(this)
    }

    private fun saveUser() {
        showAndHideProgressBarSave(true)
        addUserViewModel.addUser(
            binding?.emailUserTie?.text?.toString() ?: EMPTY_STRING,
            binding?.firstNameUserTie?.text?.toString() ?: EMPTY_STRING,
            binding?.lastNameUserTie?.text?.toString() ?: EMPTY_STRING,
            roleSelectCurrent,
            binding?.cellphoneUserTie?.text?.toString() ?: EMPTY_STRING
        )
    }

    private fun showAndHideProgressBarSave(show: Boolean) {
        binding?.saveUserBtn?.visibility = if (show) View.INVISIBLE else View.VISIBLE
        binding?.saveUserPb?.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun setUpActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_action_bar_add_user)
    }

    private fun setUpObservers() {
        addUserViewModel.validateUserData.observe(viewLifecycleOwner, ::handleAddUSer)
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(requireContext(), message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addUserViewModel.validateUserData.removeObservers(viewLifecycleOwner)
        _binding = null
    }

    override fun itemSelected(parent: AdapterView<*>?, position: Int) {
        if (!userInteraction) {
            return
        }

        userInteraction = false

        if (position > Constants.VALUE_ZERO) {
            // Obtener la clave basada en la posición seleccionada
            val selectedValue = parent?.getItemAtPosition(position).toString()
            roleSelectCurrent = allRolesMap.filterValues { it == selectedValue }.keys.first()
            println("Role selected: $selectedValue - $roleSelectCurrent")
        }
    }
}