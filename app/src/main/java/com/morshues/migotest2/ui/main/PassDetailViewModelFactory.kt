package com.morshues.migotest2.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.morshues.migotest2.repo.MigoRepository

class PassDetailViewModelFactory (
    private val migoRepository: MigoRepository,
    private val passId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PassDetailViewModel(migoRepository, passId) as T
    }
}