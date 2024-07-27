package studio.stew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StewApplication {

	public static void main(String[] args) {
		SpringApplication.run(StewApplication.class, args);
	}

}
