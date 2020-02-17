package comsis.service;

import comsis.core.model.*;
import comsis.core.serviceInterface.AuthorIndexService;
import comsis.core.serviceInterface.PublicationIndexService;
import comsis.core.serviceInterface.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IrStatisticsService implements StatisticsService {

    @Autowired
    private PublicationIndexService publicationIndexService;

    @Autowired
    private AuthorIndexService authorIndexService;

    @Override
    public List<WordFrequency> getMostFrequentTitleWords(int topCount) {
        return publicationIndexService.getMostFrequentTitleWords(topCount);
    }

    @Override
    public List<WordFrequency> getMostFrequentKeyWords(int topCount) {List<Publication> publications =
            publicationIndexService.findAll().stream().map(PublicationParser::parsePublication).collect(Collectors.toList());

        return PublicationKeyWordFrequencyCalculator.getMostFrequentKeyWords(publications, topCount).stream().sorted().limit(topCount).collect(Collectors.toList());
    }

    @Override
    public List<WordTrendModel> getKeyWordTrend(String keyWord) {
        List<Publication> publications = publicationIndexService.getPublicationByKeyWords(keyWord, Integer.MAX_VALUE).stream().map(PublicationParser::parsePublication).collect(Collectors.toList());

        return PublicationKeyWordFrequencyCalculator.calculateWordTrend(publications).stream().sorted().collect(Collectors.toList());
    }

    @Override
    public List<AuthorStatisticsData> getAuthorStatisticsData() {
        return authorIndexService.getAllAuthors()
                .stream()
                .map(AuthorParser::parseAuthor)
                .map(this::getStatisticsData)
                .sorted()
                .collect(Collectors.toList());
    }

    private AuthorStatisticsData getStatisticsData(Author author) {
        int numberOfPublications = publicationIndexService.getPublicationsByAuthor(author.getEmail(), Integer.MAX_VALUE).size();
        return new AuthorStatisticsData(author, numberOfPublications);
    }
}