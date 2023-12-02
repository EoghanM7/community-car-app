package controllers

import models.futuretrip

import persistence.Serializer

class futureTripAPI {

    class FutureTripAPI(serializerType: Serializer) {
        private var futuretrips = ArrayList<futuretrip>()
        private var serializer: Serializer = serializerType


        fun add(trip: futuretrip): Boolean {
            return futuretrips.add(trip)
        }
    }

}