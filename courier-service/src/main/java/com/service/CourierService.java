package com.service;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.model.CourierLocation;
// import com.courierservice.courier_service.repository.CourierLocationRepository;

@Service
public class CourierService
{
    // private final CourierLocationRepository _courierLocationRepository;
    private final KafkaTemplate<String, CourierLocation> kafkaTemplate;

    public CourierService(KafkaTemplate<String, CourierLocation> kafkaTemplate)
    {
        // this._courierLocationRepository = courierLocationRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public CourierLocation logLocation(String courierId, Double lat, Double lng)
    {
        CourierLocation location = new CourierLocation();
        location.setCourierId(courierId);
        location.setLat(lat);
        location.setLng(lng);
        location.setTimestamp(LocalDateTime.now());

        // _courierLocationRepository.save(location);
        try
        {
            System.out.println(location.getCourierId());
            System.out.println(location.getLat());
            System.out.println(location.getLng());
            System.out.println(location.getTimestamp());
            kafkaTemplate.send("courier-location-topic", location);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return location;

    }
}
