package com.example.consumingrest.service;

import com.example.consumingrest.dao.WeatherJdbc;
import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import com.example.consumingrest.responsemodel.openweathermap.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherService {
    @Value("${apiKey}")
    private String apiKey;
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private final CacheManager cacheManager;
    private WeatherJdbc weatherJdbc;

    @Autowired
    public WeatherService(RestTemplate restTemplate, CacheManager cacheManager, WeatherJdbc weatherJdbc) {
        this.restTemplate = restTemplate;
        this.cacheManager = cacheManager;
        this.weatherJdbc = weatherJdbc;
    }

//    @Cacheable(value = "cities", key = "'Budapest,HU'")
    public SimpleWeatherResponse getSimpleWeatherResponse() {
        return getSimpleWeatherResponse("Budapest,HU");
    }

//    @Cacheable(value = "cities", key = "#city")
    public SimpleWeatherResponse getSimpleWeatherResponse(String city) {
        List<SimpleWeatherResponse> simpleWeatherResponses = weatherJdbc.listSimpleWeatherResponse();
        if(simpleWeatherResponses.isEmpty()){
            return cashingData(city);
        }
        else if(simpleWeatherResponses.get(0).getCashedTime().plusMinutes(1).isAfter(LocalDateTime.now())){
            logger.info("Getting data from database");
            return weatherJdbc.listSimpleWeatherResponse().get(0);
        }
        weatherJdbc.deleteWeatherResponse();
        return cashingData(city);
    }

    private SimpleWeatherResponse cashingData(String city) {
        SimpleWeatherResponse simpleWeatherResponse = callApi(city);
        weatherJdbc.add(simpleWeatherResponse);
        logger.info("Getting data from API");
        return simpleWeatherResponse;
    }

    private SimpleWeatherResponse callApi(String city) {
        String url = baseUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric&lang=hu";
        logger.info("Fetching weather Data from API for '{}'...", city);
        WeatherResponse weatherResponse = restTemplate.getForObject(
                url, WeatherResponse.class);
        return new SimpleWeatherResponse(weatherResponse);
    }

    //    @CacheEvict(value = "cities", allEntries = true)
//    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRateString = "${caching.spring.citiesTTL}")
    public void emptyCitiesCache() {
        logger.info("emptying cities cache");
    }

}
