package com.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourierLocation 
{
    private String courierId;  
    private double lat;        
    private double lng;      
    private LocalDateTime timestamp;  
}
