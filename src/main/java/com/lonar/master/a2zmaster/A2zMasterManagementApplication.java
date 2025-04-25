package com.lonar.master.a2zmaster;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;



@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = { "com.lonar.master.*" })
//@PropertySource({ "classpath:persistence.properties" })
//@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
@Configuration
@EnableAsync
@EntityScan(basePackages= {"com.lonar.a2zcommons.model",  "com.lonar.master.*" })
public class A2zMasterManagementApplication extends SpringBootServletInitializer {

	public static Map<Long, Map<String, String>> configMap;
	
	public static void main(String[] args) {
		SpringApplication.run(A2zMasterManagementApplication.class, args);
		
		}
	
	@LoadBalanced
	 @Bean("resttemplate")
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }

}
