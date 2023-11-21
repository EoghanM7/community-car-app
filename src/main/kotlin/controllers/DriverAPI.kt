package controllers

import models.Driver
import persistence.Serializer
import java.util.*

class DriverAPI {
    class DriverAPI(serializerType: Serializer) {
        private var drivers = ArrayList<Driver>()
        private var serializer: Serializer = serializerType


        private fun formatListString(notesToFormat: List<Driver>): String =
            notesToFormat
                .joinToString(separator = "\n") { note ->
                    "${drivers.indexOf(note)}: $note"
                }

        fun add(driver: Driver): Boolean {
            return drivers.add(driver)
        }

        fun deleteDriver(indexToDelete: Int): Driver? {
            return if (isValidListIndex(indexToDelete, drivers)) {
                drivers.removeAt(indexToDelete)
            } else null
        }

        fun updateDriver(indexToUpdate: Int, driver: Driver): Boolean {
            val foundDriver = findDriver(indexToUpdate)

            if ((foundDriver != null) && (driver != null)) {
                foundDriver.firstName = driver.firstName
                foundDriver.secondName = driver.secondName
                foundDriver.phoneNumber = driver.phoneNumber
                foundDriver.licence = driver.licence
                return true
            }
            return false
        }

        fun searchDriversByID(driverID: Int): String {
            val matchingDrivers = drivers.filter { driver ->
                driver.driverID == driverID
            }

            return if (matchingDrivers.isNotEmpty()) {
                matchingDrivers.joinToString(separator = "\n") { driver ->
                    "${drivers.indexOf(driver)}: $driver"
                }
            } else {
                "No matching drivers found for ID: $driverID"
            }
        }
        fun searchDriversByID2(driverID: Int): String {
            val matchingDrivers = drivers.filter { driver ->
                driver.driverID == driverID
            }

            return if (matchingDrivers.isNotEmpty()) {
                matchingDrivers.joinToString(separator = "\n") { driver ->
                    driver.secondName
                }
            } else {
                "No matching drivers found for ID: $driverID"
            }
        }
        fun searchDriversByID3(driverID: Int): String {
            val matchingDrivers = drivers.filter { driver ->
                driver.driverID == driverID
            }

            return if (matchingDrivers.isNotEmpty()) {
                matchingDrivers.joinToString(separator = "\n") { driver ->
                    driver.firstName
                }
            } else {
                "No matching drivers found for ID: $driverID"
            }
        }

        fun findDriver(index: Int): Driver? {
            return if (isValidListIndex(index, drivers)) {
                drivers[index]
            } else null
        }

        fun numberOfDrivers(): Int {
            return drivers.size
        }

        fun listallDriver(): String =
            if (drivers.isEmpty()) "No drivers in system"
            else formatListString(drivers)

        private fun isValidListIndex(index: Int, list: List<Any>): Boolean {
            return (index >= 0) && (index < list.size)
        }

        fun isValidIndex(index: Int): Boolean {
            return isValidListIndex(index, drivers)
        }

        fun generateDriverID(): Int {
            var driverID = ""
            val idRng = { (0..9).random() }

            while (driverID.length < 4) {
                val number = idRng().toString()
                if (!driverID.contains(number)) driverID += number
            }
            return driverID.toInt()

        }


        @Throws(Exception::class)
        fun load() {
            drivers = serializer.read() as ArrayList<Driver>
        }

        fun save(): Boolean {
            serializer.write(drivers)
            return true
        }
    }
}
