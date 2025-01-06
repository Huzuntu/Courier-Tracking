package com.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.model.CourierLocation;
import com.repository.StoreRepository;
import com.service.ProximityService;

@Service
public class ListenerService 
{
    private final ProximityService _proximityService;
    private final StoreRepository _storeRepository;

    public ListenerService(ProximityService proximityService, StoreRepository storeRepository)
    {
        this._proximityService = proximityService;
        this._storeRepository = storeRepository;
    }

    @KafkaListener(topics = "courier-location-topic", groupId = "proximity-group", containerFactory="kafkaListenerContainerFactory")
    public void listenCourierLocation(CourierLocation location) {
        try 
        {
            _proximityService.checkProximity(
                location.getLat(),
                location.getLng(),
                location.getCourierId(),
                location.getTimestamp(),
                _storeRepository.findAll()
            );
            System.out.println(location.getCourierId());
            System.out.println(location.getLat());
            System.out.println(location.getLng());
            System.out.println(location.getTimestamp());
        }
        catch (Exception e) 
        {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
