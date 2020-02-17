package comsis.api.controller;

import comsis.core.model.AuthorStatisticsData;
import comsis.core.model.WordFrequency;
import comsis.core.model.WordTrendModel;
import comsis.core.serviceInterface.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private static final String TOP_COUNT = "10";

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping("/mostFrequentTitleWords")
    public List<String> getMostFrequentWords(@RequestParam(value = "count", defaultValue = TOP_COUNT, required = false)int count){
        return statisticsService.getMostFrequentTitleWords(count).stream().map(wf -> wf.getWord() + " -> " + wf.getFrequency()).collect(Collectors.toList());
    }

    @RequestMapping("/mostFrequentKeyWords")
    public List<String> getMostFrequentKeyWords(@RequestParam(value = "count", defaultValue = Integer.MAX_VALUE + "", required = false)int count){
        return statisticsService.getMostFrequentKeyWords(count).stream().map(wf -> wf.getWord()).collect(Collectors.toList());
    }

    @RequestMapping("/getKeyWordTrend")
    public List<WordTrendModel> getMostFrequentThemesByYear(@RequestParam(value="keyWord") String keyWord) {
        return statisticsService.getKeyWordTrend(keyWord);
    }

    @RequestMapping("/getKeyWordFrequencies")
    public List<WordFrequency> getKeyWordFrequencies(@RequestParam(value = "count", defaultValue = Integer.MAX_VALUE + "", required = false)int count){
        return statisticsService.getMostFrequentKeyWords(count).stream().map(wf -> new WordFrequency( wf.getWord(), wf.getFrequency())).collect(Collectors.toList());
    }

    @RequestMapping("/authors")
    public List<AuthorStatisticsData> getMostFrequentThemesByYear() {
        return statisticsService.getAuthorStatisticsData();
    }
}
