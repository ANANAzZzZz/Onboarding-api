package suai.vladislav.onboardingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnboardingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnboardingApiApplication.class, args);
    }

}
