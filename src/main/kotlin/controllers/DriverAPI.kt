package controllers

import models.Driver
import persistence.Serializer
import java.util.*
import kotlin.collections.ArrayList
class DriverAPI {
    class DriverAPI(serializerType: Serializer) {
        private var drivers = ArrayList<Driver>()
        private var serializer: Serializer = serializerType
    }}