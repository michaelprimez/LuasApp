package com.michaelkeskinidis.luasapp.data.provider

import java.util.*

class LocationDirectionProviderImpl : LocationDirectionProvider {

    override fun getLocation(): LocationDirection {
        val now = GregorianCalendar()
        now.timeInMillis = System.currentTimeMillis()
        return calculateLocation(now)
    }

    fun calculateLocation(now: Calendar): LocationDirection {
        val noun = GregorianCalendar(
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH),
            12,0,1)
        val midnight = GregorianCalendar(
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH),
            23,59,59)
        return if (now.after(noun) && now.before(midnight)) {
            LocationDirection("sti", "Inbound")
        } else {
            LocationDirection("mar", "Outbound")
        }
    }

}