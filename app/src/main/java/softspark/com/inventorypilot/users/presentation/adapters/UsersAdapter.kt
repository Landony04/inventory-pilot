package softspark.com.inventorypilot.users.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.databinding.ItemUserBinding
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.users.presentation.ui.utils.UserSelectedListener
import softspark.com.inventorypilot.users.utils.UserConstants.USER_STATUS_DISABLED
import softspark.com.inventorypilot.users.utils.UserConstants.USER_STATUS_ENABLED
import javax.inject.Inject

class UsersAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : ListAdapter<UserProfile, UsersAdapter.UsersViewHolder>(UsersDiffCallback()) {

    private lateinit var userSelectedListener: UserSelectedListener

    class UsersViewHolder(
        private val itemBinding: ItemUserBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            context: Context,
            userSection: UserProfile,
            userSelected: (UserProfile) -> Unit
        ) {
            with(itemBinding) {
                nameUserTv.text = String.format(
                    context.getString(R.string.text_two_values),
                    userSection.firstName,
                    userSection.lastName
                )
                roleUserTv.text = userSection.role
                cellphoneTv.text = userSection.cellPhone

                val textButton =
                    if (userSection.status == USER_STATUS_ENABLED) context.getString(R.string.text_button_disabled) else context.getString(
                        R.string.text_button_enabled
                    )
                disableUserButton.text = textButton

                disableUserButton.setOnClickListener {
                    val newStatus =
                        if (userSection.status == USER_STATUS_ENABLED) USER_STATUS_DISABLED else USER_STATUS_ENABLED
                    userSection.status = newStatus
                    userSelected(userSection)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val userCardBinding = ItemUserBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return UsersViewHolder(userCardBinding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(context = context, userSection = user) { userSelected ->
            userSelectedListener.disableOrEnabledUser(userSelected)
        }
    }

    fun initListeners(listener: UserSelectedListener) {
        userSelectedListener = listener
    }
}

class UsersDiffCallback : DiffUtil.ItemCallback<UserProfile>() {
    override fun areItemsTheSame(
        oldItem: UserProfile,
        newItem: UserProfile
    ): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserProfile,
        newItem: UserProfile
    ): Boolean {
        return oldItem == newItem
    }
}
