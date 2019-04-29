package com.onyx.microweatherbasic.service;

import com.onyx.microweatherbasic.vo.Weather;

public interface WeatherReportService {

    Weather getDataByCityId(String cityId);


}
