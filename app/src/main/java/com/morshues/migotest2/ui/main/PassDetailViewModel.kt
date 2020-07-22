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

            val timeZone = account.user.timeZone
            pass.activationTime = Calendar.getInstance()
            pass.expirationTime = Calendar.getInstance(timeZone).apply {
                if (pass.type == PassType.HOUR) {
                    add(Calendar.HOUR_OF_DAY, pass.num+1)
                } else {
                    add(Calendar.DATE, pass.num+1)
                    set(Calendar.HOUR_OF_DAY, 0)
                }
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            migoRepository.updatePass(pass)
            hintMsg.postValue("Activated")
        }
    }

}