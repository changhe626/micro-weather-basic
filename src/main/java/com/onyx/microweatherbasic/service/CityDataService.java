package com.onyx.microweatherbasic.service;

import com.onyx.microweatherbasic.vo.City;

import java.util.List;

public interface CityDataService {

    /**
     * 获取城市列表
     * @return
     * @throws RuntimeException
     */
    List<City> cityList() throws Exception;


}
