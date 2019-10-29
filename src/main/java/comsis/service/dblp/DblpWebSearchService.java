package comsis.service.dblp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import comsis.common.HttpClient;
import comsis.core.enums.DblpSearchEntity;
import comsis.core.enums.NotationFormat;
import comsis.core.model.dblp.DblpPublication;
import comsis.core.model.dblp.DblpResponse;
import comsis.core.serviceInterface.DblpSearchService;
import comsis.core.utils.DblpSearchPreferences;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class DblpWebSearchService implements DblpSearchService {

    @Override
    public String findPublicationByTitle(String title) {
        DblpSearchPreferences searchPreferences = new DblpSearchPreferences(title, DblpSearchEntity.PUBLICATIONS,
                NotationFormat.JSON, 10, 0, true);

        try {
            URL requestUrl = DblpRequestUrlBuilder.generateRequestUrl(searchPreferences);
            String jsonResponse = HttpClient.get(requestUrl);
            Gson gson = new GsonBuilder().create();
            DblpResponse<DblpPublication> response = gson.fromJson(jsonResponse, (new TypeToken<DblpResponse<DblpPublication>>(){}).getType());

            return response.getResult().getHits().getHit().length + "";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
