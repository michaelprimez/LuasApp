package com.michaelkeskinidis.luasapp

import com.michaelkeskinidis.luasapp.data.provider.LocationDirectionProviderImpl
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LocationDirectionPrroviderTest {

    private final val locationDirectionProvider = LocationDirectionProviderImpl()

    @Test
    fun `should return mar and outbound when is between midnight and noun`() {
        val now = GregorianCalendar()
        now.timeInMillis = System.currentTimeMillis()
        now.set(Calendar.HOUR, 11)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        val locationDirection = locationDirectionProvider.calculateLocation(now)
        assert(locationDirection.location == "mar")
        assert(locationDirection.direction == "Outbound")
    }

    @Test
    fun `should return sti and inbound when is between noun and midnight`() {
        val now = GregorianCalendar()
        now.timeInMillis = System.currentTimeMillis()
        now.set(Calendar.HOUR, 13)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        val locationDirection = locationDirectionProvider.calculateLocation(now)
        assert(locationDirection.location == "sti")
        assert(locationDirection.direction == "Inbound")
    }
}