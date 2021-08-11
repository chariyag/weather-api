package com.example.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRequest {
    @NotBlank(message = "apikey is not valid")
    private String apikey;
    @NotBlank(message = "city is not valid")
    private  String city;
    @NotBlank(message = "country is not valid")
    private String country;
}
