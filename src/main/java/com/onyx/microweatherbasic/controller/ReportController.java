package com.onyx.microweatherbasic.controller;

import com.onyx.microweatherbasic.service.CityDataService;
import com.onyx.microweatherbasic.service.WeatherReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("report")
public class ReportController {

    @Autowired
    private CityDataService cityDataService;
    @Autowired
    private WeatherReportService weatherReportService;


    @RequestMapping("cityId/{cityId}")
    public ModelAndView getWeatherById(@PathVariable("cityId") String cityId, Model model) throws Exception {
        model.addAttribute("title", "onyx的天气预报系统");
        model.addAttribute("cityId", cityId);
        model.addAttribute("report", weatherReportService.getDataByCityId(cityId));
        model.addAttribute("cityList", cityDataService.cityList());
        return new ModelAndView("weather/report", "reportModel", model);

    }


}
