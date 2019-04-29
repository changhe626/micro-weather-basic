package com.onyx.microweatherbasic.impl;

import com.onyx.microweatherbasic.service.CityDataService;
import com.onyx.microweatherbasic.util.XmlBuilder;
import com.onyx.microweatherbasic.vo.City;
import com.onyx.microweatherbasic.vo.CityList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CityDataServiceImpl implements CityDataService {


    @Override
    public List<City> cityList() throws Exception {
        //读取文件数据
        Resource resource = new ClassPathResource("citylist.xml");

        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();

        //文件数据转变成对象
        CityList cityList = (CityList) XmlBuilder.xmlStrToOject(CityList.class, buffer.toString());
        return cityList.getCityList();
    }


}
