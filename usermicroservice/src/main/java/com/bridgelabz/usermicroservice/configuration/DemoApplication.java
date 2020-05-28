package com.bridgelabz.usermicroservice.configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Configuration
@EnableTransactionManagement
public class DemoApplication 
{
	      
   @Bean
   public RestTemplate getRestTemplate() {
      return new RestTemplate();
   } 
  
}
