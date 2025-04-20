package gobov.roma.mvpguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("gobov.roma.mvpguide.model")
public class MvpGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(MvpGuideApplication.class, args);
    }
}