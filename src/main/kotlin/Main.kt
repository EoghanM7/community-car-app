
import controllers.DriverAPI
import controllers.TripAPI
import controllers.futureTripAPI
import models.Driver
import models.futuretrip
import models.trip
import mu.KotlinLogging
import persistence.XMLSerializer
import utitlities.ScannerInput.readNextInt
import utitlities.ScannerInput.readNextLine
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess


val driverApi = DriverAPI.DriverAPI(XMLSerializer(File("drivers.xml")))
     val tripApi = TripAPI.TripAPI(XMLSerializer(File("trips.xml")))
     val logger = KotlinLogging.logger {}


    fun main() {
        runMainMenu()
    }

    fun mainMenu(): Int {
        return readNextInt(
            """
                
                
                
                
            ╔════════════════════════════════╗
            ║        TAG Community Car       ║
            ╚════════════════════════════════╝
            ╔════════════════════════════════╗
            ║        Main Menu               ║
            ║   1.) Driver Menu              ║
            ║   2.) Trip Menu                ║
            ║   3.) Admin Menu               ║
            ║   0.) Exit                     ║
            ╚════════════════════════════════╝
            Enter Choice -> """.trimMargin(">")
        )
    }

    fun driverMenu(): Int {
        return readNextInt(
            """
                
                
                
            ╔════════════════════════════════╗
            ║        TAG Community Car       ║
            ╚════════════════════════════════╝
            ╔════════════════════════════════╗
            ║        Driver Menu             ║
            ║   1.) View Schedule            ║
            ║                                ║
            ║   2.) View Completed Trips     ║
            ║                                ║
            ║   0.) Exit                     ║
            ╚════════════════════════════════╝
            
            
            Enter Choice -> """.trimMargin(">")
        )
    }

    fun tripMenu(): Int {
        return readNextInt(
            """
                
                
                
            ╔════════════════════════════════╗
            ║        TAG Community Car       ║
            ╚════════════════════════════════╝
            ╔════════════════════════════════╗
            ║        Trip Menu               ║
            ║   1.) Start A trip             ║
            ║   2.) View future Trips        ║
            ║   3.) View previous Trips      ║
            ║   0.) Exit                     ║
            ╚════════════════════════════════╝
            Enter Choice -> """.trimMargin(">")
        )
    }

    fun adminMenu(): Int {
        return readNextInt(
            """
                
                
                
            ╔════════════════════════════════╗
            ║        TAG Community Car       ║
            ╚════════════════════════════════╝
            ╔════════════════════════════════╗
            ║        Trip Menu               ║
            ║   1.) Add a driver             ║
            ║   2.) update a Driver          ║
            ║   3.) List Driver              ║
            ║   4.) Delete a Driver          ║
            ║   0.) Exit                     ║
            ╚════════════════════════════════╝
            Enter Choice -> """.trimMargin(">")
        )
    }
fun loginMenu(): Int {
    return readNextInt(
        """
               Enter Driver ID -> """.trimMargin(">")
    )
}

    fun runMainMenu() {
        loadDriver()
        logger.info { "You are now in the main Menu." }
        do {
            when (val option = mainMenu()) {

                1 -> runDriverMenu()
                2 -> runTripMenu()
                3 -> runAdminMenu()
                0 -> exit()
                else -> println("Invalid option entered: $option")
            }
            saveDriver()
        } while (true)
    }

    fun runDriverMenu() {
        loadDriver()
        val login = readNextInt("Enter your DriverID: ")
        println("Welcome  Mr/Mrs ${driverApi.searchDriversByID2(login).replaceFirstChar{it.uppercase()}}")
        do {
            when (val option = driverMenu()) {

                1 -> viewSchedule()
                2 -> viewCompletedTrips(login)
                0 -> runMainMenu()
                else -> println("Invalid option entered: $option")
            }
            saveDriver()
        } while (true)
    }

fun viewSchedule() {
    TODO("Not yet implemented")
}


fun runTripMenu() {
        loadDriver()
        loadTrip()
        logger.info { "You are in the trip menu." }
        do {
            when (val option = tripMenu()) {

                1 -> startTrip()
                2 -> runTripMenu()
                3 -> runAdminMenu()
                0 -> runMainMenu()
                else -> println("Invalid option entered: $option")
            }
            saveDriver()
            saveTrip()
        } while (true)
    }

    fun runAdminMenu() {
        loadDriver()

        do {
            when (val option = adminMenu()) {

                1 -> addDriver()
                2 -> updateDriver()
                3 -> listDrivers()
                4 -> deleteDriver()
                5 -> scheduleTrip()
                0 -> runMainMenu()
                else -> println("Invalid option entered: $option")
            }
            saveDriver()
        } while (true)
    }

fun scheduleTrip() {
    val driverId = readNextInt("Enter Driver ID for trip: ")
    val passenger = readNextLine("Enter Passenger Name: ")
    val startLocation = readNextLine("Enter the Pickup location")
    val scheduledDateTimeInput = readNextLine("Enter scheduled date and time (yyyy-MM-dd HH:mm): ")
    val scheduledDateTime = LocalDateTime.parse(scheduledDateTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

    val scheduledTrip = futureTripAPI.add(driverId,passenger,startLocation,scheduledDateTime)
    println("Trip has been Scheduled!")
}


fun listDrivers() {
        println(driverApi.listAllDriver())
    }

    fun addDriver() {
        val firstName = readNextLine("Enter the driver's First name")
        val secondName = readNextLine("Enter the driver's Second name")
        val phoneNumber = readNextInt("Enter the driver's Mobile number: ")
        val licence = readNextInt("Enter the 9-digit driver license number")
        val driverID = driverApi.generateDriverID()

        val added = driverApi.add(Driver(firstName, secondName, phoneNumber, licence, driverID))
        if (added) {
            println("Driver added to the database")
        } else {
            println("Driver not added to the database")
        }
    }

fun startTrip() {
    val login = readNextInt("Enter your DriverID: ")
    println("Driver Selected for this trip ->  ${driverApi.searchDriversByID3(login).replaceFirstChar{it.uppercase()}}  ${driverApi.searchDriversByID2(login)}")
    val firstName = driverApi.searchDriversByID3(login).replaceFirstChar{it.uppercase()}
    val secondName = driverApi.searchDriversByID2(login).replaceFirstChar{it.uppercase()}
    val startLocation = readNextLine("Enter start Location: ")
    val destination = readNextLine("Enter the destination: ")
    val passenger = readNextLine(" Enter the name of your passenger: ")
    val startTime = Instant.now()
    println("The trip has commenced at ${Instant.now()}")

    readNextLine("Enter any key to stop the trip!")
    val endTime = Instant.now()
    println("The trip has Stopped at ${Instant.now()}")
    val added = tripApi.add(trip(login, firstName, secondName, startLocation = startLocation ,destination, passenger = passenger, startTime =startTime, endTime = endTime ))
    if (added) {
        println("Trip completed")
    } else {
        println("Trip not saved!")
    }
}


    fun deleteDriver() {
        listDrivers()
        if (driverApi.numberOfDrivers() > 0) {
            val driverToDelete = readNextInt("Enter the index of the driver you want to delete")
            val deleteDriver = driverApi.deleteDriver(driverToDelete)
            if (deleteDriver != null) {
                println("${deleteDriver.firstName} ${deleteDriver.secondName} has been removed from the system")
            } else {
                println("Unable to delete")
            }
        }
    }

    fun updateDriver() {
        listDrivers()

        if (driverApi.numberOfDrivers() > 0) {
            val indexToUpdate = readNextInt("Enter the index of the driver you wish to update")

            if (driverApi.isValidIndex(indexToUpdate)) {
                val firstName = readNextLine("Enter the driver's first name")
                val secondName = readNextLine("Enter the driver's second name")
                val phoneNumber = readNextInt("Enter the driver's phone number")
                val licence = readNextInt("Enter the driver's license number:")

                val driverID = driverApi.findDriver(indexToUpdate)

                if (driverID != null) {
                    if (driverApi.updateDriver(
                            indexToUpdate,
                            Driver(
                                firstName,
                                secondName,
                                phoneNumber,
                                licence,
                                driverID = driverID.driverID
                            )
                        )
                    ) {
                        println("Update Successful")
                    } else {
                        println("Driver not Updated")
                    }
                } else {
                    println("No drivers found")
                }
            }
        }
    }


fun exit() {
    logger.info { "Exit function invoked" }
    exitProcess(0)
}
    fun loadDriver() {
        try {
            driverApi.load()
        } catch (e: Exception) {
            System.err.println("Error reading from file: $e")
        }
    }

    fun saveDriver() {
        try {
            if (driverApi.save()) {
                println("Driver logs have been saved")
            } else {
                println("Driver logs were not saved")
            }
        } catch (e: Exception) {
            System.err.println("Error writing to file: $e")
        }
    }

fun loadTrip() {
    try {
        tripApi.loadTrip()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun saveTrip() {
    try {
        if (tripApi.saveTrip()) {
            println("Trips have been saved")
        } else {
            println("Trips were not saved")
        }
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun viewCompletedTrips(login: Int) {
    println("Completed trips for $login: ")
    println(tripApi.searchTripsById(login))
}