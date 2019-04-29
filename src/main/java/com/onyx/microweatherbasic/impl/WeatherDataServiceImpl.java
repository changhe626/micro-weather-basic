package com.onyx.microweatherbasic.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onyx.microweatherbasic.service.WeatherDataService;
import com.onyx.microweatherbasic.util.CNUtil;
import com.onyx.microweatherbasic.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 根据名字获取天气
 * http://wthrcdn.etouch.cn/weather_mini?city=武汉
 * 根据城市id获取天气
 * http://wthrcdn.etouch.cn/weather_mini?citykey=101010100
 * 获取所有城市的id列表
 * http://mobile.weather.com.cn/js/citylist.xml
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    //改造,1.使用CurrentHashMap做缓存,hashMap只能用在一面的缓存,而redis对应的是整个系统的缓存...
    //2.使用redis做缓存

    private final static String WEATHER_URL = "http://wthrcdn.etouch.cn/weather_mini?";

    private static final long TIME_OUT = 1800L; // 1800s

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String url = WEATHER_URL + "citykey=" + cityId;
        return getWeatherResponse(url);
    }


    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String url = WEATHER_URL + "city=" + cityName;
        return getWeatherResponse(url);
    }

    @Override
    public void syncWeatherDataById(String cityId) {
        String url = WEATHER_URL + "citykey=" + cityId;
        saveData2Redis(url);

    }

    /**
     * 根据网址获取通用天气结果
     * 改造:先从缓存中查询,查不到在去发送http请求.
     */
    private WeatherResponse getWeatherResponse(String url) {
        String key = url;
        String strBody = null;
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // 先查缓存，缓存有的取缓存中的数据
        if (stringRedisTemplate.hasKey(key)) {
            strBody = ops.get(key);
        } else {
            // 缓存没有，再调用服务接口来获取
            ResponseEntity<String> respString = restTemplate.getForEntity(url, String.class);
            if (respString.getStatusCodeValue() == 200) {
                strBody = respString.getBody();
            }
            // 数据写入缓存,设置过期时间
            ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
        }
        WeatherResponse response = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 保存数据到redis中去
     * @param url
     */
    private void saveData2Redis(String url) {
        String key = url;
        String strBody = null;
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // 调用服务接口来获取
        ResponseEntity<String> respString = restTemplate.getForEntity(url, String.class);
        if (respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody();
        }
        // 数据写入缓存
        ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
    }


}
