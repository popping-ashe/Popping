package com.ashe.popping.global.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableFeignClients
public class CoreFeignConfig {
	@Bean
	Encoder formEncoder(ObjectFactory<HttpMessageConverters> converters){
		return new SpringFormEncoder(new SpringEncoder(converters));
	}

	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}

}
