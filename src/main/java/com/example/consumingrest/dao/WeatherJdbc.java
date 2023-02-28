package com.example.consumingrest.dao;

import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeatherJdbc {
    private JdbcTemplate jdbcTemplate;
    private WeatherMapper weatherMapper;

    public WeatherJdbc(JdbcTemplate jdbcTemplate, WeatherMapper weatherMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.weatherMapper = weatherMapper;
    }

    public void add(SimpleWeatherResponse simpleWeatherResponse){
        String sql = "INSERT INTO WEATHER VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, simpleWeatherResponse.getCity(), simpleWeatherResponse.getTimeStamp(), simpleWeatherResponse.getFeelsLike(),
                simpleWeatherResponse.getHumidity(), simpleWeatherResponse.getPressure(), simpleWeatherResponse.getTemp(), simpleWeatherResponse.getTempMin(),
                simpleWeatherResponse.getTempMax(), simpleWeatherResponse.getDescription(), simpleWeatherResponse.getIcon(),
                simpleWeatherResponse.getCashedTime());
    }

    public List<SimpleWeatherResponse> listSimpleWeatherResponse(){
        String sql = "SELECT * FROM WEATHER";
        return jdbcTemplate.query(sql, weatherMapper);
    }

    public void deleteWeatherResponse(){
        String sql = "DELETE FROM WEATHER";
        jdbcTemplate.update(sql);
    }


}
