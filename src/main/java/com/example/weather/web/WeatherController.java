package com.example.weather.web;

import com.example.weather.model.WeatherRequest;
import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController

@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping()
    public WeatherResponse getWeather(@Valid WeatherRequest weatherRequest) {
        return weatherService.getWeather(weatherRequest);
    }
}
