package com.morshues.migotest2.db.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class PassTest {

    private lateinit var pass: Pass

    @Before
    fun setUp() {
        pass = Pass(1, 1, PassType.DAY, 3)
    }

    @Test fun test_default_values() {
        val num = 3
        val defaultPass = Pass(1, 1, PassType.HOUR, 3)
        assertEquals(PassState.ADDED, defaultPass.getState())
        assertEquals(num, defaultPass.num)
        assertNull(defaultPass.activationTime)
        assertNull(defaultPass.expirationTime)
        assertEquals(PassType.HOUR, defaultPass.type)
    }

    @Test
    fun test_state() {
        assertEquals(PassState.ADDED, pass.getState())

        pass.activate(TimeZone.getDefault())
        Thread.sleep(1000)
        assertEquals(PassState.ACTIVATED, pass.getState())

        assertNotNull(pass.expirationTime)
        pass.expirationTime = Calendar.getInstance()
        Thread.sleep(1000)
        assertEquals(PassState.EXPIRED, pass.getState())
    }

}