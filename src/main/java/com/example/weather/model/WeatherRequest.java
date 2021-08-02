package com.example.weather.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class WeatherRequest {
    @NotBlank(message = "apikey is not valid")
    private String apikey;
    @NotBlank(message = "city is not valid")
    private  String city;
    @NotBlank(message = "country is not valid")
    private String country;
}
