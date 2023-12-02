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


    @Throws(Exception::class)
    fun loadFuture() {
        futureTrips = serializer.read() as ArrayList<futureTrip>
    }

    fun saveFuture(): Boolean {
        serializer.write(futureTrips)
        return true
    }
}}