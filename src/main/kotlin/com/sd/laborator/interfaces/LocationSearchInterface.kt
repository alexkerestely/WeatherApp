package com.sd.laborator.interfaces

interface LocationSearchInterface {
    fun getLocationId(locationName: String): Array<Float>
}