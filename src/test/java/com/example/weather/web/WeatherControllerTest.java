package com.example.weather.web;

import com.example.weather.model.WeatherResponse;
import com.example.weather.model.weathermap.Weather;
import com.example.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherController.class)

public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    WeatherService weatherService;

    @Test
    public void getWeather_shouldReturn_200_When_service_request_success() throws Exception {
        WeatherResponse weatherResponse= new WeatherResponse();
        weatherResponse.setDescription("cloudy");
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get("/weather?apikey=123&city=London&country=UK").accept((MediaType.APPLICATION_JSON));
        when(weatherService.getWeather(any())).thenReturn(weatherResponse);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk()).andExpect(jsonPath("$.description").value("cloudy"));
    }

    @Test
    public void getWeather_shouldReturn_400_When_request_missingCity() throws Exception {
        WeatherResponse weatherResponse= new WeatherResponse();
        weatherResponse.setDescription("cloudy");
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get("/weather?apikey=123&country=UK").accept((MediaType.APPLICATION_JSON));
        when(weatherService.getWeather(any())).thenReturn(weatherResponse);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("required fields missing"));
    }
}
