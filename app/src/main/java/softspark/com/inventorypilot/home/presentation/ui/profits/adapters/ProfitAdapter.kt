package softspark.com.inventorypilot.home.presentation.ui.profits.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.databinding.ItemProfitBinding
import javax.inject.Inject

class ProfitAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : ListAdapter<Pair<String, Double>, ProfitAdapter.ProfitViewHolder>(ProfitDiffCallback()) {

    class ProfitViewHolder(private val binding: ItemProfitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            context: Context,
            item: Pair<String, Double>
        ) = with(binding) {
            tvDate.text = item.first
            tvProfit.text =
                String.format(context.getString(R.string.text_pay_for_item), "${item.second}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfitViewHolder {
        val binding = ItemProfitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProfitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfitViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, item)
    }
}

class ProfitDiffCallback : DiffUtil.ItemCallback<Pair<String, Double>>() {
    override fun areItemsTheSame(
        oldItem: Pair<String, Double>,
        newItem: Pair<String, Double>
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: Pair<String, Double>,
        newItem: Pair<String, Double>
    ): Boolean {
        return oldItem == newItem
    }
}