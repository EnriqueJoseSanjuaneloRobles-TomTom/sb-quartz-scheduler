package com.tomtom.scheduler.demo.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;


@Configuration
public class QuartzConfiguration {

//  @Autowired
//  QuartzProperties quartzProperties;

  @Autowired
  QuartzProperties quartzProperties;

  @Bean
  public SpringBeanJobFactory springBeanJobFactory(ApplicationContext context) {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(context);
    return jobFactory;
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext context, DataSource dataSource) {

    Properties properties = new Properties();
    properties.putAll(quartzProperties.getProperties());

    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
    schedulerFactoryBean.setQuartzProperties(properties);
    schedulerFactoryBean.setJobFactory(springBeanJobFactory(context));
    schedulerFactoryBean.setDataSource(dataSource);
    return schedulerFactoryBean;
  }

}
