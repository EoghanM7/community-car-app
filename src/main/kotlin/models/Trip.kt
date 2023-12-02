package models


import java.time.Instant


data class trip(
    var driverID: Int,
    var driverName: String,
    var driverSname: String,
    var startLocation: String,
    var destination: String,
    var passenger: String,
    var startTime: Instant,
    var endTime: Instant,
    )
