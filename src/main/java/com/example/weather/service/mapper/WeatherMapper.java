package com.example.weather.service.mapper;

import com.example.weather.model.WeatherRequest;
import com.example.weather.model.WeatherResponse;
import com.example.weather.model.weathermap.WeatherMapApiResponse;
import com.example.weather.service.entity.WeatherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    @Mapping(target = "description",expression = "java(weatherMapApiResponse.getWeather().get(0).getDescription())" )
    @Mapping(target = "main" ,expression = "java(weatherMapApiResponse.getWeather().get(0).getMain())" )
    @Mapping(source="weatherRequest.country" ,target = "country" )
    @Mapping(source="weatherRequest.city" ,target = "city" )
    WeatherEntity toWeatherEntity(WeatherMapApiResponse weatherMapApiResponse, WeatherRequest weatherRequest);

    WeatherResponse toWeatherResponse(WeatherEntity weatherEntity);

}
