package com.morshues.migotest2.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.morshues.migotest2.R
import com.morshues.migotest2.db.model.Pass
import com.morshues.migotest2.db.model.PassState
import com.morshues.migotest2.db.model.PassType
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("passDuration")
fun bindPassDuration(textView: TextView, pass: Pass?) {
    textView.text = when (pass?.type) {
        PassType.HOUR ->
            textView.resources.getQuantityString(R.plurals.pass_hour, pass.num, pass.num)
        PassType.DAY ->
            textView.resources.getQuantityString(R.plurals.pass_day, pass.num, pass.num)
        else -> " - "
    }
}

@BindingAdapter("passState")
fun bindPassState(textView: TextView, passState: PassState?) {
    textView.text = when (passState) {
        PassState.ADDED -> textView.resources.getString(R.string.pass_state_added)
        PassState.ACTIVATED -> textView.resources.getString(R.string.pass_state_activated)
        PassState.EXPIRED -> textView.resources.getString(R.string.pass_state_expired)
        else -> " - "
    }
}

@BindingAdapter("passTime")
fun bindPassTime(textView: TextView, c: Calendar?) {
    textView.text = if (c != null) {
        SimpleDateFormat.getDateTimeInstance().format(c.timeInMillis)
    } else {
        " - "
    }
}