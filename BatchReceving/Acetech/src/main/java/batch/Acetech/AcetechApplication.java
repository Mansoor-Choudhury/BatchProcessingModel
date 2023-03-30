package batch.Acetech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class AcetechApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcetechApplication.class, args);
	}

}
