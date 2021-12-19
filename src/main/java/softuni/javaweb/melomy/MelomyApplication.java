package softuni.javaweb.melomy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MelomyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MelomyApplication.class, args);
    }

}
