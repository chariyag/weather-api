package com.example.weather.client;

import com.example.weather.model.weathermap.WeatherMapApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="${weathermap.service.name}" ,url = "${weathermap.service.url}")
public interface WeatherClient {

    @GetMapping(value = "",consumes = "application/json")
    WeatherMapApiResponse getWeather(@RequestParam("q") String country, @RequestParam("appid") String apiKey);
}
