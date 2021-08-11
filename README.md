## Weather Api

This api is to get weather information based on open weather map api. Need valid apikey country and city to populate weather information.
## Tech Stack
* Java 11
* Spring JPA
* Spring Boot
* Junit 5
* Mockito
* Flyway
* Feign Client
* H2
* Maven

# How to start the application
- ```mvn clean install```
- ```java -jar target\weather-api-0.0.1-SNAPSHOT.jar```
# How to test the application
* ## Swagger Specs
  * http://localhost:8090/weather-api/swagger-ui.html
* ## Curl
  *```curl -X GET "http://localhost:8090/weather-api/weather?apikey=0cea1dab4d277fabb0718fd2f21cb8ea&city=London&country=UK" -H  "accept: */*"```

 * ## Api keys for testing
    * 0cea1dab4d277fabb0718fd2f21cb8ea
    * cfee2ae551dc29e00ad70cdf964ea923
    * ffad6fb4cf18d5faf9b6d78320cae917
    * 3de9c1313557fc30ee6208bf55a3f991
    * 0338ed134d5eb5035965aca666040d69

# Assumptions/Notes
* City weather is not going to change. So open weather map api call happen only when  weather for the city is not available in the DB.
* It's appropriate to validate apikey in Api Gateway. Just include the api key validation in the api as its expected it in the  requirement.
* Only save weather related information as it's the only relevant to this api.

# Product Backlog
* Unit test coverage to 100%
* Integration Test
* Docker set up configure 



