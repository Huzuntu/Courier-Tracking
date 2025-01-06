package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.model.CourierLocation;
import com.service.CourierService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/courier")
public class CourierController 
{
    private final CourierService _courierService;

    public CourierController(CourierService courierService)
    {
        this._courierService = courierService;
    }

    @PostMapping("/location")
    public ResponseEntity<CourierLocation> logLocation(@Valid @RequestBody CourierLocation courierLocation)
    {
        CourierLocation _courierLocation = _courierService.logLocation(
            courierLocation.getCourierId(),
            courierLocation.getLat(),
            courierLocation.getLng()
        );
        return new ResponseEntity<>(_courierLocation, HttpStatus.CREATED);
    }

    // @PostMapping("/{courierId}/location")
    // public ResponseEntity<CourierLocation> logLocation(
    //         @PathVariable String courierId,
    //         @RequestParam Double latitude,
    //         @RequestParam Double longitude) {
    //     CourierLocation location = locationService.logLocation(courierId, latitude, longitude);
    //     return new ResponseEntity<>(location, HttpStatus.CREATED);
    // }
    

}
