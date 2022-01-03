package com.example.demo;

import com.example.demo.entities.Laptop;
import com.example.demo.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Ejercicio04Application {

	public static void main(String[] args) {

		//SpringApplication.run(Ejercicio04Application.class, args);

		ApplicationContext context = SpringApplication.run(Ejercicio04Application.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		//creamos laptop
		Laptop laptop1 = new Laptop(null,"Asus","F555L","15 pulgadas","500GB","4GB");
		Laptop laptop2 = new Laptop(null,"Huawei","MateBookD","15 pulgadas","1TB","8GB");

		//salvamos los laptops
		repository.save(laptop1);
		repository.save(laptop2);
	}

}
