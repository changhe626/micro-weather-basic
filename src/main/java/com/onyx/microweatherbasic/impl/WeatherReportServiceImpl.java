package com.onyx.microweatherbasic.impl;

import com.onyx.microweatherbasic.service.WeatherDataService;
import com.onyx.microweatherbasic.service.WeatherReportService;
import com.onyx.microweatherbasic.vo.Weather;
import com.onyx.microweatherbasic.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Autowired
    private WeatherDataService weatherDataService;


    @Override
    public Weather getDataByCityId(String cityId) {
        WeatherResponse data = weatherDataService.getDataByCityId(cityId);
        Weather weather = data.getData();
        return weather;
    }
}
