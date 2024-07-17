package guru.springframework.sdjpaintro.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class Bootstrap implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("I was called!..");
    }
}
