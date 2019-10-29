package comsis;

import comsis.data.entity.UserDto;
import comsis.data.entity.UserRoleDto;
import comsis.data.repositoryInterface.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import comsis.service.webCrawler.CrawlerRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ComsisIrWebApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CrawlerRunner crawlerRunner;

    public static void main(String[] args) {
        SpringApplication.run(ComsisIrWebApi.class, args);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertMockUser() {
        userRepository.save(new UserDto("admin", encoder().encode("password"), UserRoleDto.ADMIN));
        userRepository.save(new UserDto("user", "$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu", UserRoleDto.USER));
    }

    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
//    @EventListener(ApplicationReadyEvent.class)
//    public void insertMockUser() {
//        BufferedWriter writer = null;
//        try {
//            Set<PublicationPage> paperPageSet = crawlerRunner.searchForPublications();
//            writer = new BufferedWriter(new FileWriter("authors.txt"));
//            Iterator<PublicationPage> it = paperPageSet.iterator();
//
//            while (it.hasNext()) {
//                writer.write(it.next().formatForFileWriter());
//            }
//
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
