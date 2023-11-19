package models

data class trip(
    var driverID: Int,
    var startLocation: String,
    var startTime: String,
    var destination: Int,
    var passenger: Int,
    var endTime: Int,
)