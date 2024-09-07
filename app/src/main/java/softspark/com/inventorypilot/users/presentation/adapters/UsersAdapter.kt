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
import javax.inject.Inject

class UsersAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : ListAdapter<UserProfile, UsersAdapter.UsersViewHolder>(UsersDiffCallback()) {

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

                disableUserButton.setOnClickListener {
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
        }
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
