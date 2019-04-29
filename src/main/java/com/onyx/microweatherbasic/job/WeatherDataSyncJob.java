package com.onyx.microweatherbasic.job;

import com.onyx.microweatherbasic.service.CityDataService;
import com.onyx.microweatherbasic.service.WeatherDataService;
import com.onyx.microweatherbasic.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class WeatherDataSyncJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Autowired
    private WeatherDataService weatherDataService;
    @Autowired
    private CityDataService cityDataService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("同步数据开始了");
        List<City> list = null;
        try {
            list = cityDataService.cityList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (City city : list) {
            weatherDataService.syncWeatherDataById(city.getCityId());
        }
        logger.info("同步数据完成");
    }
}
