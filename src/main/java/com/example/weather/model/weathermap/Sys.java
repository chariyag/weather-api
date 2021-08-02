
package com.example.weather.model.weathermap;

import lombok.Data;

@Data
public class Sys {

    private String country;
    private Long id;
    private Double message;
    private Long sunrise;
    private Long sunset;
    private Long type;


}
