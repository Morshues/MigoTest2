package com.morshues.migotest2.db.model

import com.morshues.migotest2.db.Converters
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.SEPTEMBER
import java.util.Calendar.YEAR

class ConvertersTest {

    private val cal = Calendar.getInstance().apply {
        set(YEAR, 1998)
        set(MONTH, SEPTEMBER)
        set(DAY_OF_MONTH, 4)
    }

    private val timeZone = TimeZone.getTimeZone("GMT+9")

    private val passType = PassType.DAY

    @Test
    fun calendarToTimestamp() {
        assertEquals(cal.timeInMillis, Converters().calendarToTimestamp(cal))
    }

    @Test
    fun datestampToCalendar() {
        assertEquals(Converters().timestampToCalendar(cal.timeInMillis), cal)
    }

    @Test
    fun timeZoneToString() {
        assertEquals(timeZone.id, Converters().fromTimeZone(timeZone))
    }

    @Test
    fun stringToTimeZone() {
        assertEquals(Converters().toTimeZone(timeZone.id), timeZone)
    }

    @Test
    fun passTypeToString() {
        assertEquals("DAY", Converters().fromPathType(passType))
    }

    @Test
    fun stringToPassType() {
        assertEquals(Converters().toPassType("DAY"), passType)
    }



}