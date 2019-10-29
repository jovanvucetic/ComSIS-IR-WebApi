package comsis.service.dblp;

import comsis.core.enums.DblpSearchEntity;
import comsis.core.enums.NotationFormat;
import comsis.core.serviceInterface.DblpSearchService;
import comsis.core.utils.DblpSearchPreferences;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class DblpWebSearchService implements DblpSearchService {

    @Override
    public String findPublicationByTitle(String title) {
        DblpSearchPreferences searchPreferences = new DblpSearchPreferences(title, DblpSearchEntity.PUBLICATIONS,
                NotationFormat.JSON, 10, 0);

        try {
            URL requestUrl = DblpRequestUrlBuilder.generateRequestUrl(searchPreferences);
            return sendGetRequest(requestUrl);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String sendGetRequest(URL requestUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            System.out.println("GET request not worked");
            return null;
        }
    }
}
