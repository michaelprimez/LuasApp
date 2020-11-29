package com.michaelkeskinidis.luasapp.data.network.response

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/*
<direction name="Outbound">
	<tram dueMins="DUE" destination="Sandyford" />
	<tram dueMins="4" destination="Bride's Glen" />
	<tram dueMins="11" destination="Sandyford" />
	<tram dueMins="18" destination="Bride's Glen" />
</direction>
*/
@Xml
data class Direction(
    @Attribute val name: String,
    @Element val trams: List<Tram>
)