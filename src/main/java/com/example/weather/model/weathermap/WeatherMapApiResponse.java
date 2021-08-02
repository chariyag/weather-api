
package com.example.weather.model.weathermap;

import java.util.List;

import lombok.Data;

@Data
public class WeatherMapApiResponse {

    private String base;
    private Clouds clouds;
    private Long cod;
    private Coord coord;
    private Long dt;
    private Long id;
    private Main main;
    private String name;
    private Sys sys;
    private Long visibility;

    private List<Weather> weather;

    private Wind wind;


}
