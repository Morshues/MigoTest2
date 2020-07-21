package com.morshues.migotest2.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar

import com.morshues.migotest2.databinding.FragmentPassDetailBinding
import com.morshues.migotest2.utilities.InjectorUtils

class PassDetailFragment : Fragment() {

    private val args: PassDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentPassDetailBinding

    private val passDetailViewModel: PassDetailViewModel by viewModels {
        InjectorUtils.providePassDetailViewModelFactory(this, args.passId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPassDetailBinding.inflate(inflater, container, false).apply {
            pass = passDetailViewModel.pass.value

            setOnActivateListener {
                passDetailViewModel.activate(pass)
            }
        }

        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        passDetailViewModel.pass.observe(viewLifecycleOwner) { newPass ->
            binding.pass = newPass
        }

        passDetailViewModel.hintMsg.observe(viewLifecycleOwner) { msg ->
            Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
        }
    }
}
