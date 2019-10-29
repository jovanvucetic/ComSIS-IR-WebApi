package comsis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import comsis.service.webCrawler.CrawlerRunner;

@SpringBootApplication
public class ComsisIrWebApi {

    @Autowired
    private CrawlerRunner crawlerRunner;

    public static void main(String[] args) {
        SpringApplication.run(ComsisIrWebApi.class, args);

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
