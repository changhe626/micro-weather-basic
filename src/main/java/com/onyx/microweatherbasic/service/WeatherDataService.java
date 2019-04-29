package com.onyx.microweatherbasic.service;

import com.onyx.microweatherbasic.vo.WeatherResponse;

/**
 * 天气接口
 */
public interface WeatherDataService {

    /**
     * 根据城市id查询天气数据
     * @param cityId
     * @return
     */
    WeatherResponse getDataByCityId(String cityId);


    /**
     * 根据城市名字查询天气数据
     * @param cityName
     * @return
     */
    WeatherResponse  getDataByCityName(String cityName);


    /**
     * 根据城市id进行同步天气
     * @param cityId
     */
    void syncWeatherDataById(String cityId);

}
