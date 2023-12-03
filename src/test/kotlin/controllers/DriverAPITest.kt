package controllers

import models.Driver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DriverAPITest {

    private var eoghan: Driver? = null
    private var Michael: Driver? = null
    private var Dylan: Driver? = null
    private var Joe: Driver? = null
    private var Billy: Driver? = null
    private var fullDriver: DriverAPI? = DriverAPI(XMLSerializer(File("drivers.xml")))
    private var emptyDriver: DriverAPI? = DriverAPI(XMLSerializer(File("drivers.xml")))

    @BeforeEach
    fun setup() {
        eoghan = Driver("Eoghan", "McGee", 0, 87444000, 3704)
        Michael = Driver("Michael", "McGee", 0, 87444000, 3705)
        Dylan = Driver("Dylan", "McGee", 0, 87444000, 3706)
        Joe = Driver("Joe", "McGee", 0, 87444000, 3707)
        Billy = Driver("Billy", "McGee", 0, 87444000, 3708)
        fullDriver!!.add(eoghan!!)
        fullDriver!!.add(Michael!!)
        fullDriver!!.add(Dylan!!)
        fullDriver!!.add(Joe!!)
        fullDriver!!.add(Billy!!)
    }

    @AfterEach
    fun tearDown() {
        eoghan = null
        Michael = null
        Dylan = null
        Joe = null
        Billy = null
        fullDriver = null
        emptyDriver = null
    }

    @Nested
    inner class AddDriver {
        @Test
        fun `adding a Note to a populated list adds to ArrayList`() {
            val newDriver = Driver("Eoghan", "McGee", 0, 87444000, 3704)
            assertEquals(5, fullDriver!!.numberOfDrivers())
            assertTrue(fullDriver!!.add(newDriver))
            assertEquals(6, fullDriver!!.numberOfDrivers())
            assertEquals(newDriver, fullDriver!!.findDriver(fullDriver!!.numberOfDrivers() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`() {
            val newDriver = Driver("Eoghan", "McGee", 0, 87444000, 3704)
            assertEquals(0, emptyDriver!!.numberOfDrivers())
            assertTrue(emptyDriver!!.add(newDriver))
            assertEquals(1, emptyDriver!!.numberOfDrivers())
            assertEquals(newDriver, emptyDriver!!.findDriver(emptyDriver!!.numberOfDrivers() - 1))
        }
    }

    @Nested
    inner class ListDrivers {

        @Test
        fun `listDrivers returns no drivers stored when ArrayList is empty`() {
            assertEquals(0, emptyDriver!!.numberOfDrivers())
            assertTrue(
                emptyDriver!!.listAllDriver().lowercase().contains("no drivers in system")
            )
        }

        @Test
        fun `listDriver returns drivers when ArrayList has active Drivers stored`() {
            assertEquals(5, fullDriver!!.numberOfDrivers())
            val activeDriverString = fullDriver!!.listAllDriver().lowercase()
            assertTrue(activeDriverString.contains("eoghan"))
            assertTrue(activeDriverString.contains("michael"))
            assertTrue(activeDriverString.contains("dylan"))
            assertTrue(activeDriverString.contains("joe"))
            assertTrue(activeDriverString.contains("billy"))
        }

        @Test
        fun `Generate DriverID returns a 4 digit number`() {
            val driverID = fullDriver!!.generateDriverID()
            assertTrue(driverID in 0..9999, "Generated ID: $driverID")
        }

        @Nested
        inner class deletingDrivers {

            @Test
            fun `Deleting a driver when the arraylist is empty returns Null`() {
                assertNull(emptyDriver!!.deleteDriver(0))
                assertNull(fullDriver!!.deleteDriver(-1))
                assertNull(fullDriver!!.deleteDriver(5))
            }

            @Test
            fun `Deleting a driver that exists deletes and returns the driver deleted`() {
                assertEquals(5, fullDriver!!.numberOfDrivers())
                assertEquals(Billy, fullDriver!!.deleteDriver(4))
                assertEquals(4, fullDriver!!.numberOfDrivers())
                assertEquals(eoghan, fullDriver!!.deleteDriver(0))
                assertEquals(3, fullDriver!!.numberOfDrivers())
            }

            @Nested
            inner class UpdateDriver {
                @Test
                fun `updating a Driver that does not exist returns false`() {
                    assertFalse(
                        fullDriver!!.updateDriver(
                            6,
                            Driver(
                                "Mac",
                                "Aodh",
                                0,
                                875555,
                                3707
                            )
                        )
                    )
                    assertFalse(
                        fullDriver!!.updateDriver(
                            -1,
                            Driver(
                                "Mac",
                                "Aodh",
                                0,
                                875555,
                                3707
                            )
                        )
                    )
                    assertFalse(
                        emptyDriver!!.updateDriver(
                            0,
                            Driver(
                                "Mac",
                                "Aodh",
                                0,
                                875555,
                                3707
                            )
                        )
                    )
                }

                @Test
                fun `updating a note that exists returns true and updates`() {
                    assertEquals(Billy, fullDriver!!.findDriver(4))
                    assertEquals("Billy", fullDriver!!.findDriver(4)!!.firstName)
                    assertEquals(87444000, fullDriver!!.findDriver(4)!!.licence)
                    assertEquals(3708, fullDriver!!.findDriver(4)!!.driverID)

                    assertTrue(
                        fullDriver!!.updateDriver(
                            4,
                            Driver(
                                "Mac",
                                "Aodh",
                                0,
                                875555,
                                3709
                            )
                        )
                    )
                    assertEquals("Mac", fullDriver!!.findDriver(4)!!.firstName)
                    assertEquals(875555, fullDriver!!.findDriver(4)!!.licence)
                    assertEquals(3708, fullDriver!!.findDriver(4)!!.driverID)
                }
            }
        }

        @Nested
        inner class CountingMethods {

            @Test
            fun numberOfDrivers() {
                assertEquals(5, fullDriver!!.numberOfDrivers())
                assertEquals(0, emptyDriver!!.numberOfDrivers())
            }
        }

        @Nested
        inner class SearchingDriversByID {

            @Test
            fun `searchDriversByID2 returns matching driver's second name when driver exists`() {
                val driverApi = fullDriver

                val driverID = eoghan!!.driverID
                val expectedSecondName = "McGee"

                val result = driverApi!!.searchDriversByID2(driverID)

                assertEquals(expectedSecondName, result, "Second name does match for driver with ID $driverID")
            }
        }

        @Test
        fun `searchDriversByID3 returns matching driver's First name when driver exists`() {
            val driverApi = fullDriver

            val driverID = eoghan!!.driverID
            val expectedFirstName = "Eoghan"

            val result = driverApi!!.searchDriversByID3(driverID)

            assertEquals(expectedFirstName, result, "Second name does match for driver with ID $driverID")
        }
    }
}
