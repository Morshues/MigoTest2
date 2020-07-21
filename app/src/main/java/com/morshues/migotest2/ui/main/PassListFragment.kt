package com.morshues.migotest2.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.morshues.migotest2.adapters.PassesAdapter
import com.morshues.migotest2.databinding.FragmentPassListBinding
import com.morshues.migotest2.utilities.InjectorUtils

class PassListFragment : Fragment() {

    private lateinit var binding: FragmentPassListBinding
    private val viewModel: PassListViewModel by viewModels {
        InjectorUtils.providePassListViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPassListBinding.inflate(inflater, container, false)

        val adapter = PassesAdapter()
        binding.rvPassList.adapter = adapter
        subscribeUi(adapter)

        val navController = findNavController()

        binding.setAddItemListener {
            val direction =
                PassListFragmentDirections.actionPassListFragmentToPassCreateDialog()
            navController.navigate(direction)
            // TODO: will crash if click it several times quickly
        }

        return binding.root
    }

    private fun subscribeUi(adapter: PassesAdapter) {
        viewModel.account.observe(viewLifecycleOwner) { mAccount ->
            // Will be null before user created
            mAccount?.apply {
                adapter.submitList(passes)
            }
        }

        // Observe result value from PassCreateDialog
        val navController = findNavController()
        val handle = navController.currentBackStackEntry?.savedStateHandle
        val passCreateData
                = handle?.getLiveData<PassCreateDialog.Result?>(PassCreateDialog.ARG_RESULT)
        passCreateData?.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                viewModel.addPass(result.type, result.num)
                handle.set<PassCreateDialog.Result>(PassCreateDialog.ARG_RESULT, null)
            }
        }

    }
}
