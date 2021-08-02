package com.example.weather.service;

import com.example.weather.client.WeatherClient;
import com.example.weather.exception.DownstreamException;
import com.example.weather.model.WeatherRequest;
import com.example.weather.model.weathermap.Weather;
import com.example.weather.model.weathermap.WeatherMapApiResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherMapApiService {
    private  final WeatherClient weatherClient;

    public WeatherMapApiResponse getWeatherMap(WeatherRequest weatherRequest) {
        try {
            WeatherMapApiResponse weatherMapApiResponse = weatherClient.getWeather
                    (new StringJoiner(",").add(weatherRequest.getCity()).add(weatherRequest.getCountry()).toString(), weatherRequest.getApikey());
            validateResponse(weatherMapApiResponse);
            return  weatherMapApiResponse;
        } catch (FeignException feignException) {
            log.error("fail to get response",feignException);
            throw DownstreamException.builder().message("internal error").build();
        }
    }

    private void validateResponse(WeatherMapApiResponse weatherMapApiResponse){
        Optional.of(weatherMapApiResponse).map(WeatherMapApiResponse::getWeather)
                .orElseThrow( ()-> DownstreamException.builder().message("WeatherMapApiResponse is not valid").build());

    }

}
