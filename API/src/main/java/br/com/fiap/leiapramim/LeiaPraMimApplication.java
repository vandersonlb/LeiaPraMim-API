package br.com.fiap.leiapramim;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.fiap.leiapramim.entity.Device;

@SpringBootApplication
public class LeiaPraMimApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeiaPraMimApplication.class, args);
		
		
		Device device1 = new Device(0, "rm94177", "Samsung", "g20", "30", 11, LocalDate.now());
		System.out.println(device1);
		
		
	}

}
