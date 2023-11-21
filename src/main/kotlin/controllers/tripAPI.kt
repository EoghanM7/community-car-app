package controllers

import models.trip
import persistence.Serializer

class TripAPI {

    class TripAPI(serializerType: Serializer) {
        private var trips = ArrayList<trip>()
        private var serializer: Serializer = serializerType
        fun add(trip: trip): Boolean {
            return trips.add(trip)
        }
        private fun formatListString(tripsToFormat: List<trip>): String =
            tripsToFormat
                .joinToString(separator = "\n") { trip ->
                    "${trips.indexOf(trip)}: $trip"
                }


        }
    }
