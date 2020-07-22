package com.morshues.migotest2.ui.main

import androidx.lifecycle.ViewModel
import com.morshues.migotest2.R
import com.morshues.migotest2.db.model.Pass
import com.morshues.migotest2.db.model.PassState

class PassItemViewModel(
    val pass: Pass
): ViewModel() {

    val color = when (pass.getState()) {
        PassState.ADDED -> R.color.pass_added
        PassState.ACTIVATED -> R.color.pass_activated
        PassState.EXPIRED -> R.color.pass_expired
    }

}