package com.christianahvilla.carlos.Helpers

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.collections.ArrayList


class RandomPositions {

    fun getRandomLocation(point: LatLng, radius: Int): LatLng? {
        val randomPoints: MutableList<LatLng> = ArrayList()
        val randomDistances: MutableList<Float> = ArrayList()
        val myLocation = Location("")
        myLocation.setLatitude(point.latitude)
        myLocation.setLongitude(point.longitude)

        //This is to generate 10 random points
        for (i in 0..9) {
            val x0 = point.latitude
            val y0 = point.longitude
            val random = Random()

            // Convert radius from meters to degrees
            val radiusInDegrees = radius / 111000f.toDouble()
            val u: Double = random.nextDouble()
            val v: Double = random.nextDouble()
            val w = radiusInDegrees * Math.sqrt(u)
            val t = 2 * Math.PI * v
            val x = w * Math.cos(t)
            val y = w * Math.sin(t)

            // Adjust the x-coordinate for the shrinking of the east-west distances
            val new_x = x / Math.cos(y0)
            val foundLatitude = new_x + x0
            val foundLongitude = y + y0
            val randomLatLng = LatLng(foundLatitude, foundLongitude)
            randomPoints.add(randomLatLng)
            val l1 = Location("")
            l1.setLatitude(randomLatLng.latitude)
            l1.setLongitude(randomLatLng.longitude)
            randomDistances.add(l1.distanceTo(myLocation))
        }
        //Get nearest point to the centre
        val indexOfNearestPointToCentre =
            randomDistances.indexOf(Collections.min(randomDistances))
        return randomPoints[indexOfNearestPointToCentre]
    }
}