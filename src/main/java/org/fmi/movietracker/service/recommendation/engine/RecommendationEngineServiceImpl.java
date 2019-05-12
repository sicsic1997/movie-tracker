package org.fmi.movietracker.service.recommendation.engine;
import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationEngineServiceImpl {

    public static final String ENGLISH = "ENGLISH";

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieGenreMappingRepository movieGenreMappingRepository;

    @Autowired
    private MoviePeopleRoleMappingRepository moviePeopleRoleMappingRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private SimilarityRepository similarityRepository;

    private Map<String, Integer> wordFreqMap;

    public void recalculateSimilarities() {

        wordFreqMap = new HashMap<>();
        List<Word> wordList = wordRepository.findAll();
        for (Word word : wordList) {
            wordFreqMap.putIfAbsent(word.getValue(), word.getFrequency());
        }

        Map<Movie, List<String>> data = prepareData();

        similarityRepository.deleteAll();

        calculateSimilarities(data);

    }

    private int countOccurrences(String[] list, String item) {
        int counter = 0;
        for(int i = 0; i < list.length; i ++) {
            if(list[i].toLowerCase().equals(item.toLowerCase())) {
                counter ++;
            }
        }
        return counter;
    }

    private void calculateSimilarities(Map<Movie, List<String>> data) {
        for(Movie movieA : data.keySet()) {
            for(Movie movieB : data.keySet()) {

                if(movieA.getId() >= movieB.getId()) {
                    continue;
                }

                Set<String> allWords = new HashSet<>();
                allWords.addAll(data.get(movieA));
                allWords.addAll(data.get(movieB));

                List<Integer> movieAFreqCounter = new ArrayList<>();
                List<Integer> movieBFreqCounter = new ArrayList<>();

                for (String wordValue : allWords) {
                    movieAFreqCounter.add(countOccurrences(
                        movieA.getPlot().split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"),
                        wordValue));
                }

                for (String wordValue : allWords) {
                    movieBFreqCounter.add(countOccurrences(
                        movieB.getPlot().split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"),
                        wordValue));
                }

                Similarity similarity = new Similarity();
                similarity.setMovieA(movieA);
                similarity.setMovieB(movieB);
                BigDecimal similarityValue = calculateCosinesSimilarity(movieAFreqCounter, movieBFreqCounter);
                similarity.setValue(similarityValue);
                similarityRepository.save(similarity);

                Similarity similarityTwo = new Similarity();
                similarityTwo.setMovieB(movieA);
                similarityTwo.setMovieA(movieB);
                similarityTwo.setValue(similarityValue);
                similarityRepository.save(similarityTwo);
            }
        }

    }

    private BigDecimal calculateCosinesSimilarity(List<Integer> listA, List<Integer> listB) {
        double result, scalarProduct = 0, normA = 0, normB = 0;
        for(int i = 0; i < listA.size(); i++) {
            scalarProduct += listA.get(i) * listB.get(i);
            normA += listA.get(i) * listA.get(i);
            normB += listB.get(i) * listB.get(i);
        }

        result = scalarProduct / (Math.sqrt(normA) * Math.sqrt(normB));

        return BigDecimal.valueOf(result);
    }

    private Map<Movie, List<String>> prepareData() {

        Map<Movie, List<String>> movieMap = new HashMap<>();

        List<Movie> movieList = movieRepository.findAll();
        for (Movie movie : movieList) {
            List<String> values = new ArrayList<>();

            //Adding the title
            values.addAll(getTitleNormalized(movie));

            //Adding genres
            values.addAll(getGenresCodesForMovie(movie));

            //Adding actors and directors
            values.addAll(getPeopleNameForMovieAndRoleCode(movie, RoleCode.ACTOR));
            values.addAll(getPeopleNameForMovieAndRoleCode(movie, RoleCode.DIRECTOR));

            //Adding plot keywords
            values.addAll(getKeywords(movie.getPlot()));

            movieMap.put(movie, values);
        }

        return movieMap;
    }

    private List<String> getTitleNormalized(Movie movie) {
        return Arrays.stream(movie.getPlot().split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"))
            .map(String::toLowerCase)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<String> getKeywords(String plot) {

        List<String> plotWords = Arrays.stream(plot.split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"))
            .map(String::toLowerCase)
            .collect(Collectors.toList());

        System.out.println("CHECK");

        List<Word> wordList = new ArrayList<>();
        for (String wordValue : plotWords) {
            Word word = new Word();
            word.setValue(wordValue);
            word.setFrequency(wordFreqMap.getOrDefault(wordValue, 1));
            wordList.add(word);
        }
        return wordList
            .stream()
            .sorted(Comparator.comparingInt(Word::getFrequency))
            .distinct()
            .limit(20)
            .map(Word::getValue)
            .collect(Collectors.toList());
    }

    private List<String> getPeopleNameForMovieAndRoleCode(Movie movie, RoleCode roleCode) {
        return moviePeopleRoleMappingRepository
            .getAllByRoleCodeAndMovie(roleCode.getCode(), movie)
            .stream()
            .map(MoviePeopleRoleMapping::getPeople)
            .filter(Objects::nonNull)
            .map(People::getName)
            .map(this::transformName)
            .filter(p -> !p.isEmpty())
            .collect(Collectors.toList());
    }

    private String transformName(String name) {
        if(name.isEmpty()) {
            return "";
        }
        return name.replaceAll("\\s","").toLowerCase();
    }

    private List<String> getGenresCodesForMovie(Movie movie) {
        return movieGenreMappingRepository
            .getAllByMovie(movie)
            .stream()
            .map(MovieGenreMapping::getGenre)
            .map(Genre::getCode)
            .map(String::toLowerCase)
            .collect(Collectors.toList());
    }

}
