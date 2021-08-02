package com.example.weather.constants;

import java.util.stream.Stream;

public enum ApiKeys {
    API_KEY_1("0cea1dab4d277fabb0718fd2f21cb8ea"),
    API_KEY_2("cfee2ae551dc29e00ad70cdf964ea923"),
    API_KEY_3("ffad6fb4cf18d5faf9b6d78320cae917"),
    API_KEY_4("3de9c1313557fc30ee6208bf55a3f991"),
    API_KEY_5("0338ed134d5eb5035965aca666040d69");

    String keyValue;
    ApiKeys(String keyValue){
      this.keyValue= keyValue;
    }

    public static boolean isKeyValid(String value){
       return Stream.of(ApiKeys.values()).filter(apikeyValue->apikeyValue.keyValue.equals(value)).findFirst().isPresent();
    }
}
