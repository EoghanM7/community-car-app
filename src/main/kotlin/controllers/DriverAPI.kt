package controllers

import models.Driver
import persistence.Serializer
import java.util.*

class DriverAPI {
    class DriverAPI(serializerType: Serializer) {
        private var drivers = ArrayList<Driver>()
        private var serializer: Serializer = serializerType













@Throws(Exception::class)
fun load() {
    drivers = serializer.read() as ArrayList<Driver>
}

  fun save(): Boolean{
      serializer.write(drivers)
      return true
  }
}
}
