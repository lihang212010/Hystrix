package com.example.demo;

import java.util.List;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.detDSA;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import ch.qos.logback.classic.Logger;



@RestController
public class MoviesController {
  private static final org.slf4j.Logger LOGGER=LoggerFactory.getLogger(MoviesController.class);
	@Autowired
	
	private RestTemplate restTemplate;
 @HystrixCommand(fallbackMethod = "findByIDFallback")  //使用Hystrix注解
	@RequestMapping("/user/{id}")
	public Users findById(@PathVariable Long id) {
		return this.restTemplate.getForObject("http://demo-cloud-user/"+id, Users.class);
	}	

		public Users findByIDFallback(Long id,Throwable throwable) {   //注解对应的方法
			LOGGER.error("进入回退方法的异常为："+throwable);
			Users users=new Users();
			users.setName("出错时用户");
			return users;
		}
	} 


