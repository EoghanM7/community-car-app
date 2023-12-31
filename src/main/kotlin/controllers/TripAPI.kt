package controllers

import models.trip
import persistence.Serializer
import java.util.*


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

        fun searchTripsById(login: Int): String {
            val driverTrips = trips.filter { futuretrip ->
                futuretrip.driverID == login
            }

            val numberOfTrips = driverTrips.size
            return if (driverTrips.isEmpty()) {
                "No completed trips found for driver $login."
            } else {
                "$numberOfTrips trips found for driver $login:\n" + formatListString(driverTrips)
            }
        }

        fun viewAllTrips() {
            if (trips.isEmpty()) {
                println("No trips completed")
            } else {
                val formattedList = formatListString(trips)
                println("Completed trips:\n$formattedList")
            }
        }

        @Throws(Exception::class)
        fun loadTrip() {
            trips = serializer.read() as ArrayList<trip>
        }

        fun saveTrip(): Boolean {
            serializer.write(trips)
            return true
        }
    }

