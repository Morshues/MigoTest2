package com.morshues.migotest2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morshues.migotest2.db.model.Pass
import com.morshues.migotest2.db.model.PassType
import com.morshues.migotest2.repo.MigoRepository
import kotlinx.coroutines.launch
import java.util.*

class PassDetailViewModel(
    private val migoRepository: MigoRepository,
    passId: Long
): ViewModel() {

    val pass: LiveData<Pass> = migoRepository.getPass(passId)

    fun activate(pass: Pass?) {
        if (pass == null) {
            return
        }

        val timeZone = TimeZone.getDefault() // TODO: Use user's timezone instead
        pass.activationTime = Calendar.getInstance()
        pass.expirationTime = Calendar.getInstance(timeZone).apply {
            if (pass.type == PassType.HOUR) {
                add(Calendar.HOUR_OF_DAY, pass.num+1)
            } else {
                add(Calendar.DATE, pass.num)
                set(Calendar.HOUR_OF_DAY, pass.num+1)
            }
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        viewModelScope.launch {
            migoRepository.updatePass(pass)
        }
    }

}