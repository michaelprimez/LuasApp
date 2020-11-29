package com.michaelkeskinidis.luasapp.data.network.response

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/*
* <stopInfo created="2020-11-28T08:32:23" stop="Stillorgan" stopAbv="STI">
	<message>Green Line services operating normally</message>
	<direction name="Inbound">
		<tram dueMins="9" destination="Broombridge" />
	</direction>
	<direction name="Outbound">
		<tram dueMins="DUE" destination="Sandyford" />
		<tram dueMins="4" destination="Bride's Glen" />
		<tram dueMins="11" destination="Sandyford" />
		<tram dueMins="18" destination="Bride's Glen" />
	</direction>
* </stopInfo>
*/
@Xml
data class StopInfo(
    @Attribute val created: String,
    @Attribute val stop: String,
    @PropertyElement val message: String,
    @Element val direction: List<Direction>
)