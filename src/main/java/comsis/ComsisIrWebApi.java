package comsis;

import comsis.core.serviceInterface.AuthorService;
import comsis.core.serviceInterface.PublicationService;
import comsis.data.repositoryInterface.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ComsisIrWebApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthorService crawlerRunner;

    public static void main(String[] args) {
        SpringApplication.run(ComsisIrWebApi.class, args);
    }

    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

//  @EventListener(ApplicationReadyEvent.class)
//    public void insertMockUser() {
//     crawlerRunner.indexAuthors();
//        //crawlerRunner.indexPublications();
//   }
}
