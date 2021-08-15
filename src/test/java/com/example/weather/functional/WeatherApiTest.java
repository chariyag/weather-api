package com.example.weather.functional;


import com.example.weather.client.WeatherClient;
import com.example.weather.model.weathermap.Weather;
import com.example.weather.model.weathermap.WeatherMapApiResponse;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.service.entity.WeatherEntity;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class WeatherApiTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private WeatherClient weatherClient;
    @Autowired
    private WeatherRepository weatherRepository;

    @BeforeEach
    public void init() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void should_get_whether_from_weathermapapi_when_db_donot_have_relevant_info(){
        Weather weather=new Weather();
        weather.setDescription("cloudy");
        WeatherMapApiResponse weatherMapApiResponse = new WeatherMapApiResponse();
        weatherMapApiResponse.setWeather(List.of(weather));
        when(weatherClient.getWeather(anyString(),anyString())).thenReturn(weatherMapApiResponse);

        given()
            .queryParam("apikey","0cea1dab4d277fabb0718fd2f21cb8ea")
            .queryParam("city","London")
            .queryParam("country","UK")
        .when()
             .get("/weather")
        .then()
             .status(HttpStatus.OK)
             .contentType(ContentType.JSON)
             .assertThat().body("description",equalTo("cloudy"));
    }

    @Test
    public void should_get_whether_from_db_when_db_have_relevant_info(){
        WeatherEntity weather=new WeatherEntity();
        weather.setDescription("description1");
        weather.setCity("city1");
        weather.setCountry("country1");
        weatherRepository.saveAndFlush(weather);

        given()
           .queryParam("apikey","0cea1dab4d277fabb0718fd2f21cb8ea")
           .queryParam("city","city1")
           .queryParam("country","country1")
        .when()
           .get("/weather")
        .then()
            .status(HttpStatus.OK)
            .contentType(ContentType.JSON)
            .assertThat().body("description",equalTo("description1"));
    }

    @Test
    public void should_get_400_when_city_does_not_exists_in_request(){
        given()
           .queryParam("apikey","0cea1dab4d277fabb0718fd2f21cb8ea")
           .queryParam("city","")
           .queryParam("country","country1")
        .when()
           .get("/weather")
        .then()
           .status(HttpStatus.BAD_REQUEST)
           .contentType(ContentType.JSON)
           .assertThat().body("message",equalTo("required fields missing"),
                "errors",hasItems("city"));
    }

    @Test
    public void should_get_400_when_city_country_apikey_does_not_exists_in_request(){
        given()
            .queryParam("apikey","")
            .queryParam("city","")
            .queryParam("country","")
        .when()
            .get("/weather")
        .then()
            .status(HttpStatus.BAD_REQUEST)
            .contentType(ContentType.JSON)
            .assertThat().body("message",equalTo("required fields missing"),
                "errors",hasItems("apikey","city","country"));
    }
}
