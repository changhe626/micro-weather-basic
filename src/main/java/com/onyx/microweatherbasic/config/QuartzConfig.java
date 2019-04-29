package com.onyx.microweatherbasic.config;

import com.onyx.microweatherbasic.job.WeatherDataSyncJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {


    private final static int TIME = 1800;//30min

    @Bean
    public JobDetail jobDetail() {
        JobDetail detail = JobBuilder.newJob(WeatherDataSyncJob.class)
                .withDescription("weatherData").storeDurably().build();
        return detail;
    }


    @Bean
    public Trigger trigger() {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(TIME).repeatForever();

        Trigger build = TriggerBuilder.newTrigger().
                forJob(jobDetail()).withIdentity("trigger").withSchedule(builder).build();
        return build;
    }


}
