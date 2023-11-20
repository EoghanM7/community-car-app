import controllers.DriverAPI
import models.Driver
import mu.KotlinLogging
import persistence.XMLSerializer
import utitlities.ScannerInput.readNextInt
import utitlities.ScannerInput.readNextLine
import java.io.File
import kotlin.system.exitProcess


    private val driverApi = DriverAPI.DriverAPI(XMLSerializer(File("drivers.xml")))
    private val logger = KotlinLogging.logger {}


    fun main(args: Array<String>) {
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
            ==>>""".trimMargin(">")
        )
    }

    fun DriverMenu(): Int {
        return readNextInt(
            """
            ╔════════════════════════════════╗
            ║        TAG Community Car       ║
            ╚════════════════════════════════╝
            ╔════════════════════════════════╗
            ║        Driver Menu             ║
            ║   1.) Check in                 ║
            ║   2.) View Schedule            ║
            ║   3.) View previous Trips      ║
            ║   0.) Exit                     ║
            ╚════════════════════════════════╝
            ==>>""".trimMargin(">")
        )
    }

    fun TripMenu(): Int {
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
            ==>>""".trimMargin(">")
        )
    }

    fun AdminMenu(): Int {
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
            ==>>""".trimMargin(">")
        )
    }

    fun runMainMenu() {
        loadDriver()
        logger.info { "Drivers and Trip data loaded." }
        do {
            when (val option = mainMenu()) {

                1 -> runDriverMenu()
                2 -> runTripMenu()
                3 -> runAdminMenu()
                0 -> exitProcess(0)

            }
            saveDriver()
        } while (true)
    }

    fun runDriverMenu() {
        loadDriver()
        logger.info { "Drivers and Trip data loaded." }
        do {
            when (val option = DriverMenu()) {

                1 -> checkIn()
                2 -> viewSchedule()
                3 -> viewPreviousTrips()
                0 -> exitProcess(0)

            }
            saveDriver()
        } while (true)
    }

    private fun checkIn() {
        TODO("Not yet implemented")
    }

    private fun viewSchedule() {
        TODO("Not yet implemented")
    }

    private fun viewPreviousTrips() {
        TODO("Not yet implemented")
    }

    fun runTripMenu() {
        loadDriver()
        logger.info { "Drivers and Trip data loaded." }
        do {
            when (val option = TripMenu()) {

                1 -> runDriverMenu()
                2 -> runTripMenu()
                3 -> runAdminMenu()
                0 -> exitProcess(0)

            }
            saveDriver()
        } while (true)
    }

    fun runAdminMenu() {
        loadDriver()
        logger.info { "Drivers and Trip data loaded." }
        do {
            when (val option = AdminMenu()) {

                1 -> addDriver()
                2 -> listDrivers()
                3 -> updateDriver()
                4 -> deleteDriver()
                0 -> exitProcess(0)

            }
            saveDriver()
        } while (true)
    }

    fun listDrivers() {
        println(driverApi.listallDriver())
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
