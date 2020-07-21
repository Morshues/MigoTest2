package com.morshues.migotest2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morshues.migotest2.db.model.PassType
import com.morshues.migotest2.db.model.UserWithPasses
import com.morshues.migotest2.repo.MigoRepository
import kotlinx.coroutines.launch

class PassListViewModel(
    private val migoRepository: MigoRepository
) : ViewModel() {

    val account: LiveData<UserWithPasses> = migoRepository.getAccount()

    fun addPass(passType: PassType, num: Int) {
        viewModelScope.launch {
            migoRepository.insertPass(passType, num)
        }
    }
}
