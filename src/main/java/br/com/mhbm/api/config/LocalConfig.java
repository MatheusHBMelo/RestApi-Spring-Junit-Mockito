package br.com.mhbm.api.config;

import br.com.mhbm.api.models.User;
import br.com.mhbm.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig implements CommandLineRunner {
    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "Matheus Henrique", "matheus@email.com", "12345");
        User user2 = new User(null, "Davi Jose", "davi@email.com", "67890");
        repository.saveAll(List.of(user1, user2));
    }
}
