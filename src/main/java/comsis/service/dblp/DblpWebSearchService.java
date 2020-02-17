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
import java.util.List;

@Service
public class DblpWebSearchService implements DblpSearchService {

    @Override
    public DblpPublication findPublicationByTitle(String title) {
        DblpSearchPreferences searchPreferences = new DblpSearchPreferences(title, DblpSearchEntity.PUBLICATIONS,
                NotationFormat.JSON, 10, 0, true);

        DblpResponse<DblpPublication> response = getDblpResponse(searchPreferences);

        if(response == null) {
            return null;
        }

        return response.getFirstHitObject();
    }

    @Override
    public List<DblpPublication> findPublicationsForAuthor(String authorsName) {
        DblpSearchPreferences searchPreferences = new DblpSearchPreferences(authorsName, DblpSearchEntity.PUBLICATIONS,
                NotationFormat.JSON, Integer.MAX_VALUE, 0, true);

        DblpResponse<DblpPublication> response = getDblpResponse(searchPreferences);

        if(response == null) {
            return null;
        }

        return response.getHitObjectsList();
    }

    private DblpResponse<DblpPublication> getDblpResponse(DblpSearchPreferences searchPreferences){
        try {
            URL requestUrl = DblpRequestUrlBuilder.generateRequestUrl(searchPreferences);
            String jsonResponse = HttpClient.get(requestUrl);
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(jsonResponse, (new TypeToken<DblpResponse<DblpPublication>>(){}).getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
