package com.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import com.grum.geocalc.*;
import com.model.Stores;

@Service
public class ProximityService
{
private static final double RADIUS_THRESHOLD_METERS = 100.0;

    private final ConcurrentHashMap<String, Long> lastEntryTime = new ConcurrentHashMap<>();

    public void checkProximity(double courierLat, double courierLng, String courierId, LocalDateTime eventTimestamp, List<Stores> stores) {
        for (Stores store : stores) {
            if (isWithinProximity(courierLat, courierLng, store.getLat(), store.getLng())) {
                String key = courierId + "_" + store.getName();

                // Check reentry condition (1 minute)
                Long lastEntry = lastEntryTime.get(key);
                if (lastEntry == null || Duration.between(Instant.ofEpochMilli(lastEntry), eventTimestamp).toMinutes() >= 1) {
                    logEntry(courierId, store);
                    lastEntryTime.put(key, eventTimestamp.toInstant(ZoneOffset.UTC).toEpochMilli());
                }
            }
            else
            {
                System.out.println("Courier is not in the radius of the store");
            }
        }
    }

    /**
     * Determines if a courier is within the radius of a store.
     *
     * @param courierLat Latitude of the courier
     * @param courierLng Longitude of the courier
     * @param storeLat   Latitude of the store
     * @param storeLng   Longitude of the store
     * @return true if the courier is within the threshold, false otherwise
     */
    private boolean isWithinProximity(double courierLat, double courierLng, double storeLat, double storeLng) {
        Coordinate courierLatCoord = Coordinate.fromDegrees(courierLat);
        Coordinate courierLngCoord = Coordinate.fromDegrees(courierLng);
        Point courierPoint = Point.at(courierLatCoord, courierLngCoord);

        Coordinate storeLatCoord = Coordinate.fromDegrees(storeLat);
        Coordinate storeLngCoord = Coordinate.fromDegrees(storeLng);
        Point storePoint = Point.at(storeLatCoord, storeLngCoord);

        double distance = EarthCalc.haversine.distance(courierPoint, storePoint); // in meters
        return distance <= RADIUS_THRESHOLD_METERS;
    }

    /**
     * Logs the entry of a courier into a store's proximity.
     *
     * @param courierId Courier ID
     * @param store     Store details
     */
    private void logEntry(String courierId, Stores store) {
        System.out.println("Courier " + courierId + " entered store: " + store.getName());
        // Additional logging or database storage can be added here
    }
}



