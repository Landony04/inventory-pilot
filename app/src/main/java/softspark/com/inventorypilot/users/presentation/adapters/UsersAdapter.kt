package softspark.com.inventorypilot.users.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.utils.Constants
import softspark.com.inventorypilot.databinding.ItemUserBinding
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.users.entities.UserChangePayload
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

                bindUserName(userSection.firstName, userSection.lastName, context)
                bindUserRole(userSection.role, context)
                bindCellphone(userSection.cellPhone)
                bindStatusButton(userSection.status, context)

                disableUserButton.setOnClickListener {
                    val newStatus =
                        if (userSection.status == USER_STATUS_ENABLED) USER_STATUS_DISABLED else USER_STATUS_ENABLED
                    userSection.status = newStatus
                    userSelected(userSection)
                }
            }
        }

        private fun bindUserName(firstName: String, lastName: String, context: Context) {
            itemBinding.nameUserTv.text = String.format(
                context.getString(R.string.text_two_values),
                firstName,
                lastName
            )
        }

        private fun bindUserRole(role: String, context: Context) {
            val roleForUser =
                if (role == Constants.DISPATCHER_ROLE) context.getString(R.string.text_dispatcher_role) else context.getString(
                    R.string.text_owner_role
                )
            itemBinding.roleUserTv.text = roleForUser
        }

        fun bindCellphone(cellphone: String) {
            itemBinding.cellphoneTv.text = cellphone
        }

        fun bindStatusButton(status: String, context: Context) {
            val textButton =
                if (status == USER_STATUS_ENABLED) context.getString(R.string.text_button_disabled) else context.getString(
                    R.string.text_button_enabled
                )
            itemBinding.disableUserButton.text = textButton
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

    override fun onBindViewHolder(
        holder: UsersViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (val latestPayload = payloads.lastOrNull()) {
            is UserChangePayload.Cellphone -> holder.bindCellphone(
                latestPayload.newCellphone
            )

            is UserChangePayload.Status -> holder.bindStatusButton(
                latestPayload.newStatus,
                context
            )

            else -> onBindViewHolder(holder, position)
        }
    }

    fun initListeners(listener: UserSelectedListener) {
        userSelectedListener = listener
    }

    fun updateItem(position: Int, newUser: UserProfile) {
        currentList.toMutableList()[position].apply {
            cellPhone = newUser.cellPhone
            status = newUser.status
        }

        notifyItemChanged(position)
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

    override fun getChangePayload(oldItem: UserProfile, newItem: UserProfile): Any? {
        return when {
            oldItem.cellPhone != newItem.cellPhone -> {
                UserChangePayload.Cellphone(newItem.cellPhone)
            }

            oldItem.status != newItem.status -> {
                UserChangePayload.Status(newItem.status)
            }

            else -> super.getChangePayload(oldItem, newItem)
        }
    }
}
