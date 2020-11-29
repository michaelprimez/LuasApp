package com.michaelkeskinidis.luasapp.data.network.response

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

/*
<tram dueMins="DUE" destination="Sandyford" />
 */
@Xml
data class Tram(
    @Attribute val dueMins: String,
    @Attribute val destination: String
)