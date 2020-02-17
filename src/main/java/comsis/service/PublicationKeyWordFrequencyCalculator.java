package comsis.service;

import comsis.core.model.Publication;
import comsis.core.model.WordFrequency;
import comsis.core.model.WordTrendModel;

import java.util.*;
import java.util.stream.Collectors;

public class PublicationKeyWordFrequencyCalculator {

    public static List<WordFrequency> getMostFrequentKeyWords(List<Publication> publications, int topCount) {
        if(publications == null) {
            return new ArrayList<>();
        }

        Map<String, Integer> frequencyMap = new HashMap<>();

        String[] keyWords = publications.stream().flatMap(publication -> Arrays.stream(publication.getKeyWords())).toArray(String[] :: new);

        Arrays.stream(keyWords).filter(word -> word != null && word.length() > 0).forEach(word -> {
            if(!frequencyMap.containsKey(word)) {
                frequencyMap.put(word, 1);
            } else {
                frequencyMap.put(word, frequencyMap.get(word) + 1);
            }
        });

        return frequencyMap
                .entrySet()
                .stream()
                .map(entry -> new WordFrequency(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static  List<WordTrendModel> calculateWordTrend(List<Publication> publications){
        Map<Integer, Integer> frequencyPerYear = new HashMap<>();
        for(Publication publication : publications) {
            int publicationYear = publication.getYear();

            if(frequencyPerYear.containsKey(publicationYear)){
                frequencyPerYear.put(publicationYear, frequencyPerYear.get(publicationYear) + 1);
            }
            else {
                frequencyPerYear.put(publicationYear, 1);
            }
        }
        int minYear = frequencyPerYear.entrySet().stream()
                .map(entry -> entry.getKey()).min(Integer::compareTo).get();
        int maxYear = frequencyPerYear.entrySet().stream()
                .map(entry -> entry.getKey()).max(Integer::compareTo).get();

        List<WordTrendModel> wordTrendModels = new LinkedList<>();
        for(int i = minYear; i <= maxYear; i++) {
            if(!frequencyPerYear.containsKey(i)) {
                frequencyPerYear.put(i, 0);
            }
            wordTrendModels.add(new WordTrendModel(i, frequencyPerYear.get(i)));
        }

        return wordTrendModels;


    }
}
