package models

import java.time.LocalDateTime

data class futuretrip(
    var driverID: Int,
    var passenger: String,
    var startLocation: String,
    var scheduledStartTime: LocalDateTime,



    )