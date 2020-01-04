package com.example.weatherapplication;

import org.junit.Test;

import java.util.List;

import database.entities.Station;
import database.entities.WeatherData;

import static org.junit.Assert.*;

public class JSONParserUnitTest {
    private static Double delta = 0.01;
    private static DataFromURLParser parser = new DataFromURLParser();

    @Test
    public void ParseJSONForChartData_isCorrect() {
        String data = "[{\"time\":\"2020-01-01 00:00:00\",\"utc\":\"2019-12-31 22:00:35\",\"data\":{\"ta\":\"5.5\"}},{\"time\":\"2020-01-01 00:01:00\",\"utc\":\"2019-12-31 22:01:23\",\"data\":{\"ta\":\"5.4\"}}]";
        List<Double> list = parser.ParseJSONForChartData(data, "ta");
        Double t1 = list.get(0);
        Double t2 = list.get(1);
        assertEquals(5.5, t1, delta);
        assertEquals(5.4, t2, delta);
    }
    @Test
    public void ParseJSONForStationsData_isCorrect() {
        String data = "[{\"station\":\"s000\",\"name\":\"WFiIS AGH\",\"long\":\"19.9129\",\"lati\":\"50.0670\",\"alti\":\"220\"},{\"station\":\"s002\",\"name\":\"I LO Kraków\",\"long\":\"19.933\",\"lati\":\"50.057\",\"alti\":\"213\"}]";
        List<Station> list = parser.ParseJSONForStationsData(data);

        Station station1 = list.get(0);
        assertEquals("s000", station1.id);
        assertEquals("WFiIS AGH", station1.name);

        Station station2 = list.get(1);
        assertEquals("s002", station2.id);
        assertEquals("I LO Kraków", station2.name);
    }
    @Test
    public void ParseJSONForWeatherData_isCorrect() {
        String data = "[{\"time\":\"2020-01-04 22:56:00\",\"station\":\"s000\",\"utc\":\"2020-01-04 20:56:01\",\"data\":{\"ws\":\"1.7\",\"wg\":\"4.8\",\"wd\":\"315\",\"ta\":\"2.5\",\"t0\":\"2.4\",\"ha\":\"99\",\"p0\":\"1015.0\",\"r1\":\"0.3\",\"ra\":\"2.1\",\"h0\":\"220\"}},{\"time\":\"2020-01-04 22:56:00\",\"station\":\"s000\",\"utc\":\"2020-01-04 20:56:49\",\"data\":{\"ws\":\"2.7\",\"wg\":\"4.1\",\"wd\":\"315\",\"ta\":\"2.5\",\"t0\":\"2.4\",\"ha\":\"99\",\"p0\":\"1015.1\",\"r1\":\"0.3\",\"ra\":\"2.1\",\"h0\":\"220\"}}]";
        WeatherData weather_data = parser.ParseJSONForWeatherData(data);

        assertEquals("s000", weather_data.station_id);
        assertEquals("2020-01-04 22:56:00", weather_data.local_time);
        assertEquals(2.5, weather_data.temperature, delta);
        assertEquals(1.7, weather_data.wind_speed, delta);
    }
}

