package softspark.com.inventorypilot.home.presentation.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.OWNER_ROLE
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_ROLE_PREFERENCE
import softspark.com.inventorypilot.databinding.ActivityHomeBinding
import softspark.com.inventorypilot.home.presentation.ui.products.viewModel.SessionViewModel
import softspark.com.inventorypilot.navigation.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityHomeBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var inventoryPilotPreferences: InventoryPilotPreferences

    @Inject
    lateinit var dialogBuilder: DialogBuilder

    private lateinit var navigator: Navigator

    private val sessionViewModel: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        navigator = Navigator()

        initNavController()
        initBottomNavBar()
        setUpObservers()
    }

    private fun doLogout() {
        sessionViewModel.doLogout()
    }

    private fun handleLogoutResult(result: Result<Boolean>) {
        when (result) {
            is Result.Error -> showToast(getString(R.string.text_failure_logout))
            Result.Loading -> println("Mostrar progress")
            is Result.Success -> {
                inventoryPilotPreferences.clearPreferences()
                showToast(getString(R.string.text_success_logout))
                navigator.navigateToLogin(this)
            }
        }
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initBottomNavBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Configurar el BottomNavigationView con NavController
        NavigationUI.setupWithNavController(homeBinding.bottomNavigation, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)

        val userRole = inventoryPilotPreferences.getValuesString(USER_ROLE_PREFERENCE)
        val addUsersItem = menu?.findItem(R.id.action_add_users)
        val usersItem = menu?.findItem(R.id.action_users)
        val addCategoryItem = menu?.findItem(R.id.action_add_categories)

        addUsersItem?.isVisible = userRole == OWNER_ROLE
        usersItem?.isVisible = userRole == OWNER_ROLE
        addCategoryItem?.isVisible = userRole == OWNER_ROLE

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_users -> {
                navController.navigate(R.id.navigation_add_user)
                true
            }

            R.id.action_users -> {
                navController.navigate(R.id.navigation_users)
                true
            }

            R.id.action_add_categories -> {
                navController.navigate(R.id.navigation_add_categories)
                true
            }

            R.id.action_profits -> {
                navController.navigate(R.id.navigation_see_profits)
                true
            }

            R.id.action_logout -> {
                dialogBuilder.showAlertDialog(
                    this,
                    getString(R.string.text_title_logout),
                    getString(R.string.text_message_logout),
                    getString(R.string.text_yes),
                    getString(R.string.text_no),
                    { doLogout() },
                    { }
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(message: String) {
        dialogBuilder.showToast(this, message)
    }

    private fun setUpObservers() {
        sessionViewModel.logoutData.observe(this, ::handleLogoutResult)
    }
}