import controllers.DriverAPI
import controllers.FutureTripsAPI
import controllers.TripAPI
import models.Driver
import models.futureTrip
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

val driverApi = DriverAPI(XMLSerializer(File("drivers.xml")))

val tripApi = TripAPI(XMLSerializer(File("trips.xml")))
val futureTripsAPI = FutureTripsAPI(XMLSerializer(File("futureTrips.xml")))
val logger = KotlinLogging.logger {}

/**
 * The main function that kicks off the TAG Community Car application.
 * It calls the [runMainMenu] function to start the main menu interface.
 */
fun main() {
    runMainMenu()
}

/**
 * Displays the main menu and prompts the user for a choice.
 *
 * @return The user's choice as an integer.
 */
fun mainMenu(): Int {
    return readNextInt(
        """
        ...
        """.trimMargin(">")
    )
}

/**
 * Displays the driver menu and prompts the user for a choice.
 *
 * @return The user's choice as an integer.
 */
fun driverMenu(): Int {
    return readNextInt(
        """
        ...
        """.trimMargin(">")
    )
}

/**
 * Displays the trip menu and prompts the user for a choice.
 *
 * @return The user's choice as an integer.
 */
fun tripMenu(): Int {
    return readNextInt(
        """
        ...
        """.trimMargin(">")
    )
}

/**
 * Displays the admin menu and prompts the user for a choice.
 *
 * @return The user's choice as an integer.
 */
fun adminMenu(): Int {
    return readNextInt(
        """
        ...
        """.trimMargin(">")
    )
}

/**
 * Runs the main menu loop, allowing the user to navigate through different sections of the application.
 * The loop continues until the user chooses to exit the application.
 */
fun runMainMenu() {
    loadDriver()
    loadTrip()
    loadFuture()
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
        saveTrip()
        saveFuture()
    } while (true)
}

/**
 * Runs the driver menu loop, allowing the driver to view their schedule and completed trips.
 * The loop continues until the driver chooses to return to the main menu.
 */
fun runDriverMenu() {
    loadTrip()
    loadDriver()
    loadFuture()
    val login = readNextInt("Enter your DriverID: ")
    println("Welcome  Mr/Mrs ${driverApi.searchDriversByID2(login).replaceFirstChar { it.uppercase() }}")
    do {
        when (val option = driverMenu()) {
            1 -> viewSchedule(login)
            2 -> viewCompletedTrips(login)
            0 -> runMainMenu()
            else -> println("Invalid option entered: $option")
        }
        saveDriver()
        saveTrip()
        saveFuture()
    } while (true)
}

/**
 * Runs the trip menu loop, allowing the user to start trips, schedule trips, and view future and previous trips.
 * The loop continues until the user chooses to exit the application.
 */
fun runTripMenu() {
    loadDriver()
    loadTrip()
    loadFuture()
    logger.info { "You are in the trip menu." }
    do {
        when (val option = tripMenu()) {
            1 -> startTrip()
            2 -> scheduleTrip()
            3 -> listAllScheduled()
            4 -> viewPreviousTrips()
            0 -> exit()
            else -> println("Invalid option entered: $option")
        }
        saveDriver()
        saveTrip()
        saveFuture()
    } while (true)
}

/**
 * Displays the list of previous trips.
 */
fun viewPreviousTrips() {
    println(tripApi.viewAllTrips())
}

/**
 * Displays the list of all scheduled future trips.
 */
fun listAllScheduled() {
    println(futureTripsAPI.listAllFutureTrips())
}

/**
 * Runs the admin menu loop, allowing the user to add, update, list, and delete drivers.
 * The loop continues until the user chooses to return to the main menu.
 */
fun runAdminMenu() {
    loadDriver()
    loadTrip()
    loadFuture()
    do {
        when (val option = adminMenu()) {
            1 -> addDriver()
            2 -> updateDriver()
            3 -> listDrivers()
            4 -> deleteDriver()
            0 -> runMainMenu()
            else -> println("Invalid option entered: $option")
        }
        saveDriver()
        saveTrip()
        saveFuture()
    } while (true)
}

/**
 * Displays the schedule for a specific driver.
 *
 * @param login The driver's ID for whom the schedule is to be displayed.
 */
fun viewSchedule(login: Int) {
    println(futureTripsAPI.searchSchedTripsById(login))
}

/**
 * Allows the user to schedule a future trip by entering the required details.
 */
fun scheduleTrip() {
    val driverId = readNextInt("Enter Driver ID for trip: ")
    val passenger = readNextLine("Enter Passenger Name: ")
    val startLocation = readNextLine("Enter the Pickup location")
    val scheduledDateTimeInput = readNextLine("Enter scheduled date and time (yyyy-MM-dd HH:mm): ")
    val scheduledDateTime = LocalDateTime.parse(scheduledDateTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

    val futureTrip = futureTrip(driverId, passenger, startLocation, scheduledDateTime)
    val addedTo = futureTripsAPI.addTrip(futureTrip)

    if (addedTo) {
        println("Trip Scheduled")
    } else {
        println("Trip Not scheduled!")
    }
}

/**
 * Displays the list of all drivers.
 */
fun listDrivers() {
    println(driverApi.listAllDriver())
}

/**
 * Allows the user to add a new driver by entering the required details.
 */
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

/**
 * Allows the user to start a trip by entering the required details.
 */
fun startTrip() {
    val login = readNextInt("Enter your DriverID: ")
    println(
        "Driver Selected for this trip ->  ${
            driverApi.searchDriversByID3(login).replaceFirstChar { it.uppercase() }
        }  ${driverApi.searchDriversByID2(login)}"
    )
    val firstName = driverApi.searchDriversByID3(login).replaceFirstChar { it.uppercase() }
    val secondName = driverApi.searchDriversByID2(login).replaceFirstChar { it.uppercase() }
    val startLocation = readNextLine("Enter start Location: ")
    val destination = readNextLine("Enter the destination: ")
    val passenger = readNextLine(" Enter the name of your passenger: ")
    val startTime = Instant.now()
    println("The trip has commenced at ${Instant.now()}")

    readNextLine("Enter any key to stop the trip!")
    val endTime = Instant.now()
    println("The trip has Stopped at ${Instant.now()}")
    val added = tripApi.add(
        trip(
            login,
            firstName,
            secondName,
            startLocation = startLocation,
            destination,
            passenger = passenger,
            startTime = startTime,
            endTime = endTime
        )
    )
    if (added) {
        println("Trip completed")
    } else {
        println("Trip not saved!")
    }
}

/**
 * Allows the user to delete a driver by selecting the driver from the list.
 */
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

/**
 * Allows the user to update a driver's information by selecting the driver from the list.
 */
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

/**
 * Exits the application and logs the exit event.
 */
fun exit() {
    logger.info { "Exit function invoked" }
    exitProcess(0)
}

/**
 * Loads driver data from the XML file.
 */
fun loadDriver() {
    try {
        driverApi.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Saves driver data to the XML file.
 */
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

/**
 * Loads trip data from the XML file.
 */
fun loadTrip() {
    try {
        tripApi.loadTrip()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Saves trip data to the XML file.
 */
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

/**
 * Loads future trip data from the XML file.
 */
fun loadFuture() {
    try {
        futureTripsAPI.loadFuture()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Saves future trip data to the XML file.
 */
fun saveFuture() {
    try {
        if (futureTripsAPI.saveFuture()) {
            println("Trips have been saved")
        } else {
            println("Trips were not saved")
        }
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Displays completed trips for a specific driver.
 *
 * @param login The driver's ID for whom the completed trips are to be displayed.
 */
fun viewCompletedTrips(login: Int) {
    println("Completed trips for $login: ")
    println(tripApi.searchTripsById(login))
}