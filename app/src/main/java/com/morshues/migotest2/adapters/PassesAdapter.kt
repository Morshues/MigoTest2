package com.morshues.migotest2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.morshues.migotest2.databinding.ItemPassBinding
import com.morshues.migotest2.db.model.Pass
import com.morshues.migotest2.ui.main.PassItemViewModel
import com.morshues.migotest2.ui.main.PassListFragmentDirections

class PassesAdapter : ListAdapter<Pass, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassHolder {
        val binding = ItemPassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PassHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pass = getItem(position)
        (holder as PassHolder).bind(pass)
    }

    class PassHolder(
        private val binding: ItemPassBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setItemClickListener {
                binding.viewModel?.let { viewModel ->
                    navigateToPass(viewModel.pass, it)
                }
            }
        }

        private fun navigateToPass(
            pass: Pass,
            it: View
        ) {
            val direction =
                PassListFragmentDirections.actionPassListFragmentToPassDetailFragment(
                    pass.id
                )
            it.findNavController().navigate(direction)
        }

        fun bind(item: Pass) {
            binding.apply {
                viewModel =
                    PassItemViewModel(item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pass>() {
            override fun areItemsTheSame(oldItem: Pass, newItem: Pass): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pass, newItem: Pass): Boolean {
                return oldItem == newItem
            }
        }
    }
}