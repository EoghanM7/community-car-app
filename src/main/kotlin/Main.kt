import controllers.DriverAPI
import persistence.XMLSerializer
import utitlities.ScannerInput.readNextInt
import java.io.File

class CarManageApp {
    private val driverApi = DriverAPI.DriverAPI(XMLSerializer(File("drivers.xml")))

    fun main() {
        runMainMenu()
    }

    fun runMainMenu() {

    }

    fun mainMenu(): Int {
        return readNextInt(
            """
            ╔════════════════════════════════╗
            ║        TAG Community Car       ║
            ╚════════════════════════════════╝
            ╔════════════════════════════════╗
            ║        Main Menu               ║
            ║                                ║
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
            ║        Main Menu               ║
            ║    1.) Check in                ║ 
            ║                                ║
            ║    2.) View Schedule           ║              
            ║                                ║
            ║    3.) View previous Trips     ║
            ║                                ║
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
            ║    1.) Start A trip            ║ 
            ║                                ║
            ║    2.) View future Trips       ║              
            ║                                ║
            ║    3.) View previous Trips     ║
            ║                                ║
            ║   0.) Exit                     ║
            ╚════════════════════════════════╝
            ==>>""".trimMargin(">")
        )
    }

    fun runDriverMenu() {
        // Implement driver menu logic
        println("Driver Menu")
    }

    fun runTripMenu() {
        // Implement trip menu logic
        println("Trip Menu")
    }

    fun runAdminMenu() {
        // Implement admin menu logic
        println("Admin Menu")
    }
}