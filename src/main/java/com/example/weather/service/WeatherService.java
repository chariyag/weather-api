package com.example.weather.service;


import com.example.weather.model.WeatherRequest;
import com.example.weather.model.WeatherResponse;
import com.example.weather.service.entity.WeatherEntity;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.model.weathermap.WeatherMapApiResponse;
import com.example.weather.service.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final ApiKeyService apiKeyService;
    private final WeatherMapApiService weatherMapApiService;
    private final WeatherMapper weatherMapper;

    private  final WeatherRepository weatherRepository;
    public WeatherResponse getWeather(WeatherRequest weatherRequest){
        apiKeyService.validateApikey(weatherRequest.getApikey());
        Optional<WeatherEntity> existingWeather = weatherRepository.findOneByCountryAndCity( weatherRequest.getCountry(),  weatherRequest.getCity());
        WeatherEntity weatherEntity;
        if(existingWeather.isPresent()){
            log.debug("weather already exist in DB");
            weatherEntity= existingWeather.get();
        }else{
            log.debug("weather info not available in DB");
            WeatherMapApiResponse weatherMapApiResponse=weatherMapApiService.getWeatherMap(weatherRequest);

            WeatherEntity weatherEntityToSave=weatherMapper.toWeatherEntity(weatherMapApiResponse,weatherRequest);
            weatherEntity = weatherRepository.saveAndFlush(weatherEntityToSave);
        }

        return weatherMapper.toWeatherResponse(weatherEntity) ;
    }


}
