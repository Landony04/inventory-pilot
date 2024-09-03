package softspark.com.inventorypilot.common.utils.components

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import softspark.com.inventorypilot.R

class MenuProviderUtils : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        // Inflar el menú si se necesita
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        // Manejar las acciones del menú aquí
        return false
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isVisible = false
        }
    }
}