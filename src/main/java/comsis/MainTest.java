package comsis;

import comsis.core.exception.InvalidAffiliationException;
import comsis.core.model.comsis.Affiliation;
import comsis.core.model.comsis.Author;
import comsis.service.webCrawler.AuthorsPageDataParser;

import java.util.List;
import java.util.Map;

public class MainTest {
    public static void main(String []args) throws InvalidAffiliationException {
        String authorsSection = "Inshil Doh<sup>1</sup>, Jiyoung Lim<sup>2</sup>, Shi Li<sup>1</sup> and Kijoon Chae<sup>1</sup>";
        String affiliationSection = "<li>Departament de Sistemes Informatics i Computacio, Universitat Politecnica de Valencia<br>Camı de Vera s/n. 46022, Valencia, Spain<br>rimache@gmail.com</li>\n" +
                "<li>{jalberola,jsuch,vbotti,aespinos,agarcia}@dsic.upv.es</li>";

        //Map<Integer, Affiliation> map = AuthorsPageDataParser.getAffiliations(affiliationSection);
        System.out.println("ee");
    }
}
