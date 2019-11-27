package comsis.service.webCrawler;

import comsis.core.exception.InvalidAffiliationException;
import comsis.core.model.comsis.Affiliation;
import comsis.core.model.Author;
import java.util.*;
import java.util.stream.Collectors;

public class AuthorsPageDataParser {
    private static final String AUTHORS_PARSING_REGEX = "<sup>(\\d)*(,\\d)*(,\\s\\d)*</sup>(,)?(\\sand)?";

    public static List<Author> parseAuthors(String authorsSection, String affiliationsSection) throws InvalidAffiliationException {
        List<Author> authorsList = new ArrayList<>();
        Map<Integer, Affiliation> affiliations = getAffiliations(affiliationsSection);
        String[] namesOfAuthors = authorsSection.split(AUTHORS_PARSING_REGEX);

        for(String author : namesOfAuthors) {
            try{
                int affiliationReference = getAffiliationReference(author, authorsSection);
                Affiliation affiliation = affiliations.get(affiliationReference);
                String email =  affiliation.getEmails().remove(0);
                String institution = affiliation.getInstitution();
                authorsList.add(new Author(author, email, institution));
            }
            catch (NullPointerException e) {
                System.out.println("NullPointerException");
               throw new InvalidAffiliationException();
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println("IndexOutOfBoundsException");
                throw new InvalidAffiliationException();
            }
            catch (NumberFormatException e) {
                System.out.println("NumberFormatException");
                throw new InvalidAffiliationException();

            }
        }

        return authorsList;
    }

    private static int getAffiliationReference(String author, String authorsSection) throws NumberFormatException {
        int authorsIndex = authorsSection.indexOf(author);
        int affiliationReferenceFirstIndex = authorsSection.indexOf("<sup>", authorsIndex);
        int affiliationReferenceLastIndex = authorsSection.indexOf("</sup>", affiliationReferenceFirstIndex);

        String authorAffiliationReference = authorsSection.substring(affiliationReferenceFirstIndex, affiliationReferenceLastIndex)
                .replace("<sup>", "")
                .replace("</sup>", "")
                .split(",")[0].trim();

        return Integer.parseInt(authorAffiliationReference);
    }

    private static Map<Integer, Affiliation> getAffiliations(String affiliationsSection){
        String[] affiliations = affiliationsSection.split("</li>\n?<li>");

        Map<Integer, Affiliation> map = new HashMap<>();
        for(int i = 1; i <= affiliations.length ; i++){
            String affiliationContent = affiliations[i-1]
                    .replace("<li>", " ")
                    .replace("</li>", " ")
                    .replace("<br>", " ")
                    .replace(", ", ",");

            String institution = parseInstitution(affiliations[i-1]);

            List<String> tokens = Arrays.stream(affiliationContent.split(" "))
                    .filter(token -> token.contains("@"))
                    .collect(Collectors.toList());

            List<String> emails = new ArrayList<>();
            for(String token : tokens) {
                emails.addAll(parseEmails(token));
            }

            map.put(i, new Affiliation(institution, emails));
        }

        return map;
    }

    private static String parseInstitution(String affiliationSection) {
        int lastLineIndex = affiliationSection.replace("<br>", "\n").lastIndexOf("\n");

        if(lastLineIndex > 0) {
        return affiliationSection.substring(0, lastLineIndex)
                .replace("<br>", "")
                .replace("<li>", "")
                .replace("</li>", "");
        }

        return "";
    }

    private static List<String> parseEmails(String token){
        List<String> emails = new ArrayList<String>();
        if(!token.contains(",")) {
            emails.add(token);
            return emails;
        }

        if(!token.contains("{") && !token.contains("}")) {
            return Arrays.asList(token.split(","));
        }

        return parseCompositeEmail(token);
    }

    private static List<String> parseCompositeEmail(String token) {
        int atSymbolPosition = token.indexOf('@');

        if(atSymbolPosition == -1) {
            System.out.println("Token " + token + " is not valid email address");
        }

        String emailDomain = token.substring(atSymbolPosition, token.length() - 1);
        return Arrays.stream(token.split(","))
                .map(email -> formatEmailAddress(email, emailDomain))
                .collect(Collectors.toList());
    }

    private static String formatEmailAddress(String email, String emailDomain) {
        email = email.replaceAll("(\\{|}|,)", "").trim();

        return email.contains("@") ? email : email + emailDomain;
    }
}
