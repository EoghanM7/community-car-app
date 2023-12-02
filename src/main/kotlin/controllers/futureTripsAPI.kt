package controllers

import models.futureTrip
import persistence.Serializer


class FutureTripsAPI{
class FutureTripsAPI(serializerType: Serializer) {

    private var futureTrips = ArrayList<futureTrip>()
    private var serializer: Serializer = serializerType



        fun addTrip(trip: futureTrip): Boolean {
            return futureTrips.add(trip)
        }

    fun listAllFutureTrips(): String =
        if (futureTrips.isEmpty()) "No drivers in system"
        else formatListString(futureTrips)


















    private fun formatListString(tripsToFormat: List<futureTrip>): String =
        tripsToFormat
            .joinToString(separator = "\n") { futureTrip ->
                "${futureTrips.indexOf(futureTrip)}: $futureTrip"
            }
    private fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0) && (index < list.size)
    }
    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, futureTrips)
    }

    @Throws(Exception::class)
    fun loadFuture() {
        futureTrips = serializer.read() as ArrayList<futureTrip>
    }

    fun saveFuture(): Boolean {
        serializer.write(futureTrips)
        return true
    }
}}