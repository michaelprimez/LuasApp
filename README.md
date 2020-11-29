# LuasApp

## Use cases

1. Given I am a LUAS passenger
When I open the app from 00:00 – 12:00
Then I should see trams forecast from Marlborough LUAS stop towards Outbound
Marlborough

http://luasforecasts.rpa.ie/xml/get.ashx?action=forecast&amp;stop=mar&amp;encrypt=false
2. Given I am a LUAS passenger
When I open the app from 12:01 – 23:59
Then I should see trams forecast from Stillorgan LUAS stop towards Inbound
Stillorgan
http://luasforecasts.rpa.ie/xml/get.ashx?action=forecast&amp;stop=sti&amp;encrypt=false


