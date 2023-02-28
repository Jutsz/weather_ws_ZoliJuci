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

    public List<SimpleWeatherResponse> listSimpleWeatherResponseByCity(String city){
        String sql = "SELECT * FROM WEATHER WHERE city = ?";
        return jdbcTemplate.query(sql, weatherMapper, city);
    }

    public void deleteWeatherResponseByCity(String city){
        String sql = "DELETE FROM WEATHER WHERE city = ?";
        jdbcTemplate.update(sql, city);
    }


}
