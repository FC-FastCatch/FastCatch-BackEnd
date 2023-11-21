package kr.co.fastcampus.fastcatch;

import kr.co.fastcampus.fastcatch.domain.init.service.InitAccommodationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FastcatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastcatchApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InitAccommodationService initAccommodationService) {
		return args -> initAccommodationService.loadJsonAndInsertData();
	}

}
