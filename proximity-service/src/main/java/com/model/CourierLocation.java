package com.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CourierLocation 
{
    private String courierId;  
    private double lat;        
    private double lng;      
    private LocalDateTime timestamp;  
}
