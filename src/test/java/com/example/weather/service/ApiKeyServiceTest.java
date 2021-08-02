package com.example.weather.service;

import com.example.weather.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class ApiKeyServiceTest {

    @InjectMocks
    ApiKeyService apiKeyService;

    @BeforeEach
    public  void init(){
        ReflectionTestUtils.setField(apiKeyService, "apikeyHourlyLimit", 5);
    }

    @Test
    public void validateApikey_shouldNot_throw_exception_ForValidApikey(){

       Assertions.assertDoesNotThrow(()->apiKeyService.validateApikey("0cea1dab4d277fabb0718fd2f21cb8ea"));
    }

    @Test
    public void validateApikey_should_throw_exception_ForInvalidApikey(){
        Assertions.assertThrows(ValidationException.class,()->apiKeyService.validateApikey("invalid"));
    }
}
