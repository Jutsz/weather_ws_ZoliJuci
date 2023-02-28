package com.example.consumingrest.dao;

import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class WeatherMapper implements RowMapper<SimpleWeatherResponse> {

    @Override
    public SimpleWeatherResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        SimpleWeatherResponse simpleWeatherResponse = new SimpleWeatherResponse();
        simpleWeatherResponse.setCity(rs.getString("city"));
        simpleWeatherResponse.setTimeStamp(rs.getLong("time_stamp"));
        simpleWeatherResponse.setFeelsLike(rs.getDouble("feels_like"));
        simpleWeatherResponse.setHumidity(rs.getDouble("humidity"));
        simpleWeatherResponse.setPressure(rs.getDouble("pressure"));
        simpleWeatherResponse.setTemp(rs.getDouble("temp"));
        simpleWeatherResponse.setTempMin(rs.getDouble("temp_min"));
        simpleWeatherResponse.setTempMax(rs.getDouble("temp_max"));
        simpleWeatherResponse.setDescription(rs.getString("description"));
        simpleWeatherResponse.setIcon(rs.getString("icon"));
        simpleWeatherResponse.setCashedTime(rs.getTimestamp("cashed_time").toLocalDateTime());

        return simpleWeatherResponse;
    }
}
