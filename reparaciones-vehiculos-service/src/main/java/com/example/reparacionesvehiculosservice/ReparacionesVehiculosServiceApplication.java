package com.example.reparacionesvehiculosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReparacionesVehiculosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReparacionesVehiculosServiceApplication.class, args);
	}

}
