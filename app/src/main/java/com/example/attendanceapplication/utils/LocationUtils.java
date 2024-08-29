package com.example.attendanceapplication.utils;

public class LocationUtils {

    private static final double EARTH_RADIUS = 6371.0; // Radius of the Earth in kilometers

    /**
     * Calculate the distance between two latitudes and longitudes
     *
     * @param lat1 Latitude of the first point
     * @param lon1 Longitude of the first point
     * @param lat2 Latitude of the second point
     * @param lon2 Longitude of the second point
     * @return Return distance in kilometers
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Converting latitude and longitude to radians
        double radLat1 = Math.toRadians(lat1);
        double radLon1 = Math.toRadians(lon1);
        double radLat2 = Math.toRadians(lat2);
        double radLon2 = Math.toRadians(lon2);

        // Calculate the difference between latitude and longitude
        double deltaLat = radLat2 - radLat1;
        double deltaLon = radLon2 - radLon1;

        // Applying the Haversine formula
        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                + Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance;
    }
}