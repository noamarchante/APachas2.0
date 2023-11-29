package daw.project.apachas.configuration;

import daw.project.apachas.entity.User;
import daw.project.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    @Autowired
    private RUser rUser;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Override
    public void run(String... args) throws IOException {
        this.rUser.deleteAll();
        User laura = new User(0, "laura", "Blanco", "lauraBlanco", securityConfiguration.passwordEncoder().encode("abc123."), "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        laura.setUserVerified(true);

        User juanma = new User(0, "Juan Manuel", "Fuentes", "juanmafuentes", securityConfiguration.passwordEncoder().encode("abc123."), "juanmafuentes@gmail.com",new Timestamp(12) , null, "USER", "");
        juanma.setUserVerified(true);

        User marcos = new User(0, "Marcos", "García", "marcosgarcia", securityConfiguration.passwordEncoder().encode("abc123."), "marcosgarcia@gmail.com",new Timestamp(12) , null, "USER", "");
        marcos.setUserVerified(true);

        User jony = new User(0, "Jonatan Alberto", "Gomez", "jonygomez", securityConfiguration.passwordEncoder().encode("abc123."), "jonygomez@gmail.com",new Timestamp(12) , null, "USER", "");
        jony.setUserVerified(true);

        User marcus = new User(0, "Marcos", "Fernández", "marcoscriado", securityConfiguration.passwordEncoder().encode("abc123."), "marcoscriado@gmail.com",new Timestamp(12), null, "USER", "");
        marcus.setUserVerified(true);

        User millan = new User(0, "Millán", "Puga", "millanpuga", securityConfiguration.passwordEncoder().encode("abc123."), "millanpuga@gmail.com",new Timestamp(12) , null, "USER", "");
        millan.setUserVerified(true);

        User cris = new User(0, "Cristina", "Ferreiro", "crisferreiro", securityConfiguration.passwordEncoder().encode("abc123."), "crisferreiro@gmail.com", new Timestamp(12), null, "USER", "");
        cris.setUserVerified(true);

        User miguel = new User(0, "Miguel", "Ferreiro", "miguelferreiro", securityConfiguration.passwordEncoder().encode("abc123."), "miguelferreiro@gmail.com",new Timestamp(12) , null, "USER", "");
        miguel.setUserVerified(true);

        User brais = new User(0, "Brais", "Fontán", "braisfontan", securityConfiguration.passwordEncoder().encode("abc123."), "braisfontan@gmail.com",new Timestamp(12) , null, "USER", "");
        brais.setUserVerified(true);

        User iago = new User(0, "Iago", "Fernandez", "iagoFernandez", securityConfiguration.passwordEncoder().encode("abc123."), "iagofernandez@gmail.com",new Timestamp(12) , null, "USER", "");
        iago.setUserVerified(true);

        User david = new User(0, "David", "Vila", "davidVila", securityConfiguration.passwordEncoder().encode("abc123."), "davidvila@gmail.com",new Timestamp(12) , null, "USER", "");
        david.setUserVerified(true);

        User admin = new User(0, "admin", "admin", "admin", securityConfiguration.passwordEncoder().encode("abc123."), "admin@admin.com",new Timestamp(12) , null, "ADMIN", "");
        admin.setUserVerified(true);

        List<User> users = Arrays.asList(laura, juanma, marcos, jony, marcus, millan, cris, miguel, brais, iago, david, admin);


        this.rUser.saveAll(users);
    }
}
