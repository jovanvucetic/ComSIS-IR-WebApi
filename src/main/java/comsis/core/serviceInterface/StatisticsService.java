package comsis.core.serviceInterface;

import comsis.core.model.AuthorStatisticsData;
import comsis.core.model.WordFrequency;
import comsis.core.model.WordTrendModel;

import java.util.List;

public interface StatisticsService {

    List<WordFrequency> getMostFrequentTitleWords(int topCount);

    List<WordFrequency> getMostFrequentKeyWords(int topCount);

    List<WordTrendModel> getKeyWordTrend(String keyWord);

    List<AuthorStatisticsData> getAuthorStatisticsData();
}
