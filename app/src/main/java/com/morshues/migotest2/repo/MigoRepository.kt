package com.morshues.migotest2.repo

import com.morshues.migotest2.db.dao.AccountDao
import com.morshues.migotest2.db.dao.PassDao
import com.morshues.migotest2.db.model.Pass
import com.morshues.migotest2.db.model.PassType

class MigoRepository (
    private val accountDao: AccountDao,
    private val passDao: PassDao
){
    fun getAccount() = accountDao.getFirst()

    fun getAccount(userId: Long) = accountDao.get(userId)

    fun getPass(passId: Long) = passDao.get(passId)

    suspend fun insertPass(passType: PassType, num: Int) {
        val pass = Pass(
            userId = 1,
            type = passType,
            num = num
        )
        passDao.insert(pass)
    }

    suspend fun updatePass(pass: Pass) {
        passDao.update(pass)
    }

    companion object {

        @Volatile private var instance: MigoRepository? = null

        fun getInstance(accountDao: AccountDao, passDao: PassDao) =
            instance ?: synchronized(this) {
                instance ?: MigoRepository(accountDao, passDao).also { instance = it }
            }
    }
}