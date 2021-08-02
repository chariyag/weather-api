package com.example.weather.service.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "WEATHER")
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String country;
    private String main;
    private String description;
}
