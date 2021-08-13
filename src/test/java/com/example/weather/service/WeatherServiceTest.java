package com.example.weather.service;

import com.example.weather.model.WeatherRequest;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.service.entity.WeatherEntity;
import com.example.weather.service.mapper.WeatherMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
    @Mock
    ApiKeyService apiKeyService;
    @Mock
    WeatherMapApiService weatherMapApiService;
    @Mock
    WeatherMapper weatherMapper;
    @Mock
    WeatherRepository weatherRepository;
    @InjectMocks
    WeatherService weatherService;
    @Captor
    ArgumentCaptor<WeatherEntity> weatherEntityCapture;
    @Captor
    ArgumentCaptor<WeatherRequest> weatherRequestCaptor;



    @Test
    public void getWeather_whenWeather_existInDB_should_get_weather_fromDB(){
        WeatherEntity weatherEntity=new WeatherEntity();
        weatherEntity.setDescription("description1");

        when(weatherRepository.findOneByCountryAndCity(any(),any())).thenReturn(Optional.of(weatherEntity));
        weatherService.getWeather(new WeatherRequest());

        verify(weatherMapper).toWeatherResponse(weatherEntityCapture.capture());
        WeatherEntity weatherEntityForMapper = weatherEntityCapture.getValue();
        Assertions.assertEquals(weatherEntity.getDescription(),weatherEntityForMapper.getDescription());

    }

    @Test
    public void getWeather_whenWeather_Not_existInDB_should_get_weather_Api(){
        WeatherEntity weatherEntity=new WeatherEntity();
        weatherEntity.setDescription("description1");

        when(weatherRepository.findOneByCountryAndCity(any(),any())).thenReturn(Optional.empty());
        when(weatherMapper.toWeatherEntity(any(),any())).thenReturn(weatherEntity);
        weatherService.getWeather(new WeatherRequest("apikey1","city1","country1"));

        verify(weatherMapApiService).getWeatherMap(weatherRequestCaptor.capture());
        WeatherRequest weatherRequest = weatherRequestCaptor.getValue();
        Assertions.assertEquals("apikey1",weatherRequest.getApikey());
        verify(weatherRepository).saveAndFlush(weatherEntityCapture.capture());
        WeatherEntity weatherEntityForSave = weatherEntityCapture.getValue();
        Assertions.assertEquals(weatherEntity.getDescription(),weatherEntityForSave.getDescription());

    }


}
