package com.example.weather.service;

import com.example.weather.model.WeatherRequest;
import com.example.weather.model.WeatherResponse;
import com.example.weather.model.weathermap.Weather;
import com.example.weather.model.weathermap.WeatherMapApiResponse;
import com.example.weather.service.entity.WeatherEntity;
import com.example.weather.service.mapper.WeatherMapper;
import com.example.weather.service.mapper.WeatherMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeatherMapperImpl.class)
public class WeatherMapperTest {
    @Autowired
    private WeatherMapper weatherMapper;

    @Test
    public void toWeatherEntity_successfullyConvert(){
        Weather weather = new Weather();
        weather.setDescription("description1");
        weather.setMain("main1");
        WeatherMapApiResponse weatherMapApiResponse = new WeatherMapApiResponse();
        weatherMapApiResponse.setWeather(List.of(weather));

        WeatherRequest weatherRequest =new WeatherRequest("apikey1","city1","country1");

        WeatherEntity weatherEntity = weatherMapper.toWeatherEntity(weatherMapApiResponse,weatherRequest);
        Assertions.assertEquals(weather.getDescription(),weatherEntity.getDescription());
        Assertions.assertEquals(weather.getMain(),weatherEntity.getMain());
        Assertions.assertEquals(weatherRequest.getCountry(),weatherEntity.getCountry());
        Assertions.assertEquals(weatherRequest.getCity(),weatherEntity.getCity());

    }

    @Test
    public void toWeatherResponse_successfullyConvert(){
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setDescription("description1");
        WeatherResponse weatherResponse = weatherMapper.toWeatherResponse(weatherEntity);
        Assertions.assertEquals(weatherEntity.getDescription(),weatherResponse.getDescription());

    }

    @Test
    public void toWeatherResponse_for_null_request(){
        Assertions.assertNull( weatherMapper.toWeatherResponse(null));

    }

    @Test
    public void toWeatherEntity_for_null_request(){
        Assertions.assertNull( weatherMapper.toWeatherEntity(null,null));

    }

}
