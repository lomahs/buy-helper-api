package bhn.buyhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuyHelperApplication {

    public static void main(String[] args) {
        try {
            Thread.sleep(15 * 1000);
            SpringApplication.run(BuyHelperApplication.class, args);
        } catch (Exception e) {
        }
    }

}