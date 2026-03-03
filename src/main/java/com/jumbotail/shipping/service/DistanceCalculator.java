package com.jumbotail.shipping.service;

import com.jumbotail.shipping.domain.Location;

public final class DistanceCalculator {

    private static final int EARTH_RADIUS_KM = 6371;

    private DistanceCalculator() {
    }

    public static double distanceInKm(Location from, Location to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Locations must not be null");
        }

        double latDistance = Math.toRadians(to.getLatitude() - from.getLatitude());
        double lonDistance = Math.toRadians(to.getLongitude() - from.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(from.getLatitude()))
                * Math.cos(Math.toRadians(to.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}

