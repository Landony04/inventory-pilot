package softspark.com.inventorypilot.users.presentation.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.components.MenuProviderUtils
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.databinding.FragmentUsersBinding
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.users.presentation.adapters.UsersAdapter
import softspark.com.inventorypilot.users.presentation.ui.utils.UserSelectedListener
import softspark.com.inventorypilot.users.presentation.viewModel.UsersViewModel
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment(), UserSelectedListener {


    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding

    private val usersViewModel: UsersViewModel by viewModels()

    @Inject
    lateinit var usersAdapter: UsersAdapter

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initListeners()
        getInitialData()
        hideActionBarOptions()
        setUpActionBar()
        setUpObservers()
    }

    private fun getInitialData() {
        usersViewModel.getAllUsers()
    }

    private fun handleGetUsers(result: Result<List<UserProfile>>) {
        when (result) {
            is Result.Error -> {
                showAndHideProgress(false)
                showToast(getString(R.string.text_error_get_users))
            }

            is Result.Success -> {
                showAndHideProgress(false)
                usersAdapter.submitList(result.data)
            }

            Result.Loading -> showAndHideProgress(true)
        }
    }

    private fun hideActionBarOptions() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(MenuProviderUtils(), viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initAdapter() {
        binding?.usersRv?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }
    }

    private fun initListeners() {
        usersAdapter.initListeners(this)
    }

    private fun setUpActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_action_bar_users)
    }

    private fun setUpObservers() {
        usersViewModel.getUsersData.observe(viewLifecycleOwner, ::handleGetUsers)
    }

    private fun showAndHideProgress(showProgress: Boolean) = with(binding) {
        this?.usersRv?.visibility = if (showProgress) View.GONE else View.VISIBLE
        this?.usersPb?.visibility = if (showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(requireContext(), message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        usersViewModel.getUsersData.removeObservers(viewLifecycleOwner)
        _binding = null
    }

    override fun disableOrEnabledUser(user: UserProfile) {
        usersViewModel.updateUserStatus(user)
        updateUserInList(user)
    }

    private fun updateUserInList(user: UserProfile) {
        val position = usersAdapter.currentList.indexOfFirst { it.id == user.id }
        usersAdapter.updateItem(position, user)
        showToast("Usuario modificado exitosamente.")
    }
}