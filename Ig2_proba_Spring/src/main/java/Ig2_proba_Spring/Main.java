package Ig2_proba_Spring;

import Ig2_proba_Spring.storage.StorageProperties;
import Ig2_proba_Spring.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(Main.class,args);
    }
    @Bean
    CommandLineRunner init(StorageService storageService){
        return (args) -> {
            try {
                storageService.init();
            }
            catch (Exception ex){
                System.out.printf("Проблеми ініціалізації роботи з файлами"+ex.getMessage());
            }
        };
    }
}