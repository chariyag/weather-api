package com.example.weather.repository;

import com.example.weather.service.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

     Optional<WeatherEntity> findOneByCountryAndCity(String country, String city);
}
