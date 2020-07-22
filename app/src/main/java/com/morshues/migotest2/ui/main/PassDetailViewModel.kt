package com.morshues.migotest2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morshues.migotest2.db.model.Pass
import com.morshues.migotest2.db.model.PassType
import com.morshues.migotest2.repo.MigoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PassDetailViewModel(
    private val migoRepository: MigoRepository,
    passId: Long
): ViewModel() {

    val pass: LiveData<Pass> = migoRepository.getPass(passId)

    val hintMsg: MutableLiveData<String> = MutableLiveData()

    fun activate(pass: Pass?) {
        if (pass == null) {
            hintMsg.postValue("No Pass Selected")
            return
        }
        if (pass.activationTime != null) {
            hintMsg.postValue("Pass has already Activated")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val account = migoRepository.getAccount(pass.userId)
            if (account.hasActivatedPass()) {
                hintMsg.postValue("There is a pass already activated")
                return@launch
            }

            pass.activate(account.user.timeZone)

            migoRepository.updatePass(pass)
            hintMsg.postValue("Activated")
        }
    }

}