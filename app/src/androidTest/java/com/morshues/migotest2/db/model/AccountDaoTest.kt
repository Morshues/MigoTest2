package com.morshues.migotest2.db.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.morshues.migotest2.db.MigoDatabase
import com.morshues.migotest2.db.dao.AccountDao
import com.morshues.migotest2.db.dao.PassDao
import com.morshues.migotest2.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class AccountDaoTest {
    private lateinit var database: MigoDatabase
    private lateinit var accountDao: AccountDao
    private lateinit var passDao: PassDao
    private val passA = Pass(1, 1, PassType.HOUR, 2)
    private val passB = Pass(2, 1, PassType.DAY, 2, expirationTime = run {
        val c = Calendar.getInstance()
        c.add(Calendar.DATE, -1)
        c
    })
    private val passC = Pass(3, 1, PassType.HOUR, 2, activationTime = run {
        val c = Calendar.getInstance()
        c.add(Calendar.DATE, -1)
        c
    }, expirationTime = run {
        val c = Calendar.getInstance()
        c.add(Calendar.DATE, 1)
        c
    })

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking<Unit> {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, MigoDatabase::class.java).build()
        accountDao = database.accountDao()
        passDao = database.passDao()

        accountDao.insert(User())

        passDao.insert(passB)
        passDao.insert(passA)
        passDao.insert(passC)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun test_accountWithPasses() {
        val account = getValue(accountDao.getFirst())
        assertThat(account.passes.size, equalTo(3))

        assertThat(account.passes[0], equalTo(passA))
        assertThat(account.passes[1], equalTo(passB))
        assertThat(account.passes[2], equalTo(passC))
    }


    @Test
    fun test_hasActivatedPass() {
        val account = getValue(accountDao.getFirst())
        assertTrue(account.hasActivatedPass())
    }

    @Test
    fun test_passesState() {
        val account = getValue(accountDao.getFirst())
        assertThat(account.passes.size, equalTo(3))

        assertEquals(PassState.ADDED, passA.getState())
        assertEquals(PassState.EXPIRED, passB.getState())
        assertEquals(PassState.ACTIVATED, passC.getState())
    }
}