package com.morshues.migotest2.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.morshues.migotest2.R
import com.morshues.migotest2.databinding.DialogPassCreateBinding
import com.morshues.migotest2.db.model.PassType
import java.io.Serializable

class PassCreateDialog : DialogFragment() {

    private lateinit var binding: DialogPassCreateBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_pass_create,
                null,
                false
            )
            setView(binding.root)

            binding.number = 1
            binding.passType = PassType.DAY

            subscribeUI(binding)
        }.create()
    }

    private fun subscribeUI(binding: DialogPassCreateBinding) {
        binding.setOnConfirmListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.apply {
                val type = if (binding.rgPassType.checkedRadioButtonId == 1) {
                    PassType.HOUR
                } else {
                    PassType.DAY
                }
                val result = Result(type, binding.number)
                set(ARG_RESULT, result)
            }
            this.dismiss()
        }
    }

    class Result(
        val type: PassType,
        val num: Int
    ): Serializable

    companion object {
        const val ARG_RESULT = "result"
    }
}