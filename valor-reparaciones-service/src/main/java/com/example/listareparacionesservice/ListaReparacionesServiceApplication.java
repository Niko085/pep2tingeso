package com.example.listareparacionesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ListaReparacionesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListaReparacionesServiceApplication.class, args);
	}

}
