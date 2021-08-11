package com.example.weather.service;

import com.example.weather.client.WeatherClient;
import com.example.weather.exception.DownstreamException;
import com.example.weather.model.WeatherRequest;
import com.example.weather.model.weathermap.Weather;
import com.example.weather.model.weathermap.WeatherMapApiResponse;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherMapApiServiceTest {
    @InjectMocks
    WeatherMapApiService weatherMapApiService;
    @Mock
    WeatherClient weatherClient;

    @Test
    public void getWeatherMap_shouldReturn_getWeatherMap_ForValidInput(){
        Weather weather=new Weather();
        weather.setDescription("cloudy");
        WeatherMapApiResponse weatherMapApiResponse = new WeatherMapApiResponse();
        weatherMapApiResponse.setWeather(List.of(weather));
        when(weatherClient.getWeather(anyString(),anyString())).thenReturn(weatherMapApiResponse);
        WeatherMapApiResponse actualApiResponse=weatherMapApiService.
                getWeatherMap(new WeatherRequest("apikey1","city1","country1"));
        Assertions.assertEquals(weatherMapApiResponse.getWeather().get(0).getDescription()
                ,actualApiResponse.getWeather().get(0).getDescription());

    }

    @Test
    public void getWeatherMap_shouldThrow_DownStreamException_ForApiError(){
        FeignException feignException = mock(FeignException.class);
        when(weatherClient.getWeather(anyString(),anyString())).thenThrow(feignException);
        DownstreamException downstreamException = Assertions.assertThrows(DownstreamException.class,()->weatherMapApiService.
                getWeatherMap(new WeatherRequest("apikey1","city1","country1")));
        Assertions.assertEquals("internal error",downstreamException.getMessage());

    }

    @Test
    public void getWeatherMap_shouldThrow_DownStreamException_ForDownstream_ResponseError(){
        WeatherMapApiResponse weatherMapApiResponse = new WeatherMapApiResponse();
        when(weatherClient.getWeather(anyString(),anyString())).thenReturn(weatherMapApiResponse);
        DownstreamException downstreamException = Assertions.assertThrows(DownstreamException.class,()->weatherMapApiService.
                getWeatherMap(new WeatherRequest("apikey1","city1","country1")));
        Assertions.assertEquals("WeatherMapApiResponse is not valid",downstreamException.getMessage());

    }
}
