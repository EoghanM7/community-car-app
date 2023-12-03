package controllers

import models.Driver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals

class DriverAPITest{




    private var eoghan: Driver? = null
    private var Michael: Driver? = null
    private var Dylan: Driver? = null
    private var Joe: Driver? = null
    private var Billy: Driver? = null
    private var fullDriver: DriverAPI? = DriverAPI(XMLSerializer(File("drivers.xml")))
    private var emptyDriver: DriverAPI? = DriverAPI(XMLSerializer(File("drivers.xml")))




    @BeforeEach
    fun setup(){
       eoghan = Driver( "Eoghan", "McGee" , 0,87444000,3704)
        Michael = Driver( "Eoghan", "McGee" , 0,87444000,3704)
         Dylan = Driver( "Eoghan", "McGee" , 0,87444000,3704)
         Joe = Driver( "Eoghan", "McGee" , 0,87444000,3704)
         Billy = Driver( "Eoghan", "McGee" , 0,87444000,3704)
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
            val newDriver = Driver( "Eoghan", "McGee" , 0,87444000,3704)
            assertEquals(5, fullDriver!!.numberOfDrivers())
            assertTrue(fullDriver!!.add(newDriver))
            assertEquals(6, fullDriver!!.numberOfDrivers())
            assertEquals(newDriver, fullDriver!!.findDriver(fullDriver!!.numberOfDrivers() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`() {
            val newDriver = Driver( "Eoghan", "McGee" , 0,87444000,3704)
            assertEquals(0, emptyDriver!!.numberOfDrivers())
            assertTrue(emptyDriver!!.add(newDriver))
            assertEquals(1, emptyDriver!!.numberOfDrivers())
            assertEquals(newDriver, emptyDriver!!.findDriver(emptyDriver!!.numberOfDrivers() - 1))
        }
    }





}