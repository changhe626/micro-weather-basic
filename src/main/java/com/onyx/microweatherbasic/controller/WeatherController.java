package com.onyx.microweatherbasic.controller;

import com.onyx.microweatherbasic.service.WeatherDataService;
import com.onyx.microweatherbasic.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherDataService weatherDataService;



    /**
     * 这里因为tomcat的版本问题,中文汉字是非法的,所有不能使用
     * @param cityName
     * @return
     */
    @PostMapping("cityName")
    public WeatherResponse getWeatherByName(@RequestParam("cityName") String cityName){
        WeatherResponse data = weatherDataService.getDataByCityName(cityName);
        return data;
    }


    @GetMapping("cityId/{cityId}")
    public WeatherResponse getWeatherById(@PathVariable("cityId") String cityId){
        WeatherResponse data = weatherDataService.getDataByCityId(cityId);
        return data;
    }


}
