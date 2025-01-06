package com.service;

import java.time.Duration;

import java.time.LocalDateTime;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.grum.geocalc.*;
import com.model.Stores;

@Service
public class ProximityService {

    private static final double RADIUS_THRESHOLD_METERS = 100.0;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ConcurrentHashMap<String, LocalDateTime> lastEntryTime = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Double> courierTravelDistances = new ConcurrentHashMap<>();
    private final ExecutorService kafkaExecutor = Executors.newFixedThreadPool(2);

    public ProximityService(KafkaTemplate<String, String> kafkaTemplate) 
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void checkProximity(double courierLat, double courierLng, String courierId, LocalDateTime eventTimestamp, List<Stores> stores) 
    {
        for (Stores store : stores) 
        {
            processStoreProximity(courierLat, courierLng, courierId, eventTimestamp, store);
        }
    }

    private void processStoreProximity(double courierLat, double courierLng, String courierId, LocalDateTime eventTimestamp, Stores store) 
    {
        double distance = calculateDistance(courierLat, courierLng, store.getLat(), store.getLng());
        if (distance <= RADIUS_THRESHOLD_METERS) 
        {
            handleProximityEvent(courierId, store, eventTimestamp, distance);
        }
    }

    private void handleProximityEvent(String courierId, Stores store, LocalDateTime eventTimestamp, double distance) 
    {
        String key = courierId + "_" + store.getName();
        if (canLogEntry(key, eventTimestamp)) 
        {
            logEntryAsync(courierId, store);
            updateLastEntryTime(key, eventTimestamp);
        }
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) 
    {
        Coordinate latCoord1 = Coordinate.fromDegrees(lat1);
        Coordinate lngCoord1 = Coordinate.fromDegrees(lng1);
        Point point1 = Point.at(latCoord1, lngCoord1);

        Coordinate latCoord2 = Coordinate.fromDegrees(lat2);
        Coordinate lngCoord2 = Coordinate.fromDegrees(lng2);
        Point point2 = Point.at(latCoord2, lngCoord2);

        double distance = EarthCalc.haversine.distance(point1, point2);

        return distance;
    }

    private boolean canLogEntry(String key, LocalDateTime eventTimestamp) 
    {
        LocalDateTime lastEntry = lastEntryTime.get(key);
        return lastEntry == null || Duration.between(lastEntry, eventTimestamp).toMinutes() >= 1;
    }

    private void updateLastEntryTime(String key, LocalDateTime timestamp) 
    {
        lastEntryTime.put(key, timestamp);
    }

    private void logEntryAsync(String courierId, Stores store) 
    {
        String message = "Courier " + courierId + " entered store: " + store.getName();
        kafkaExecutor.submit(() -> kafkaTemplate.send("courier-location-log", message));
    }

    public void updateTravelDistance(String courierId, double distance) 
    {
        courierTravelDistances.merge(courierId, distance, Double::sum);
    }

    public Double getTotalTravelDistance(String courierId) 
    {
        return courierTravelDistances.getOrDefault(courierId, 0.0);
    }
}