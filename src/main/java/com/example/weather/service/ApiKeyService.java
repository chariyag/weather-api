package com.example.weather.service;

import com.example.weather.constants.ApiKeys;
import com.example.weather.exception.ValidationException;
import io.github.bucket4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApiKeyService {

    @Value("${apikey.hourly.limit}")
    private int apikeyHourlyLimit;


    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(apikeyHourlyLimit, Refill.intervally(apikeyHourlyLimit, Duration.ofHours(1))))
                .build();
    }
    public void validateApikey(String apiKey){
        if(!ApiKeys.isKeyValid(apiKey)){
            throw ValidationException.builder().message("apikey is not valid").build();
        } else {
            validateRequestLimit(apiKey);
        }

    }
    private void validateRequestLimit(String apikey){
        Bucket bucket = resolveBucket(apikey);
        if (!bucket.tryConsumeAndReturnRemaining(1).isConsumed()) {
            throw ValidationException.builder().message("Hourly limit has been exceeded").build();
        }
        }

}
