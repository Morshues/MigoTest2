package com.morshues.migotest2.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.morshues.migotest2.db.MigoDatabase
import com.morshues.migotest2.repo.MigoRepository
import com.morshues.migotest2.ui.main.PassDetailViewModelFactory
import com.morshues.migotest2.ui.main.PassListViewModelFactory

object InjectorUtils {
    private fun getPassRepository(context: Context): MigoRepository {
        val database = MigoDatabase.getInstance(context.applicationContext)
        return MigoRepository.getInstance(
            database.accountDao(),
            database.passDao()
        )
    }

    fun providePassListViewModelFactory(fragment: Fragment): PassListViewModelFactory {
        return PassListViewModelFactory(getPassRepository(fragment.requireContext()), fragment)
    }

    fun providePassDetailViewModelFactory(
        fragment: Fragment,
        passId: Long
    ): PassDetailViewModelFactory {
        return PassDetailViewModelFactory(getPassRepository(fragment.requireContext()), passId)
    }

}