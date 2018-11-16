# ParkMeApp

ParkMeApp allows owners of spare parking spaces in high density urban areas in Greece, to rent them to drivers who wish to park their cars in those areas. Based upon the principles of Sharing Economy, ParkMeApp aims to tackle traffic congestion issues, while providing an extra source of income for the citizens.

## Design Pattern and Principles

In order to create an application that is reusable, maintainable, scalable and easy test-debug Solid Principles (https://en.wikipedia.org/wiki/SOLID) were followed
throughout the implementation phase. Morover, to achieve seperation of concerns MVP design pattern (https://en.wikipedia.org/wiki/Model–view–presenter) was selected.

## Database - Mbaas

Google's Firebase was used to maintain user details of ParkMeApp. 

## Other APIs used

**Map**: Google maps API

**Root**: Android location Geocoder & Http request in Google's Geocoding API web service.

## Testing 
**Unit tests**: Can be found in app/src/test/java/vd/parkmeapp folder.

**UI testing**: For UI testing Firebase test lab and Android monkey tool where used.
