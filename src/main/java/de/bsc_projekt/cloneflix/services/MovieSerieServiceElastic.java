package de.bsc_projekt.cloneflix.services;

import de.bsc_projekt.cloneflix.Models.ElasticModels.Title;
import de.bsc_projekt.cloneflix.Repositories.Elasticsearch.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * https://stackoverflow.com/questions/34580033/spring-io-autowired-the-blank-final-field-may-not-have-been-initialized
 * final fields können nicht mit @Autowired initialisiert werden. Das ist
 * widersprüchlich. Dafür braucht man diesen Konstruktor. Mit dem von lombok
 * generierten funktioniert das nicht.
 */
@Service
public class MovieSerieServiceElastic {

    private final SearchRepository searchRepository;

    @Autowired
    public MovieSerieServiceElastic(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void addMovieSerie(String id, String title, String[] genres, String[] actors, String posterPath,
            String model) {
        if (searchRepository.existsById(id)) {
            System.out.println("present");
        }
        else {
            Title movieElastic = new Title(id, title, genres, actors, posterPath, model);
            searchRepository.save(movieElastic);
            System.out.println("added");
        }
    }

    public void deleteMovieSerie(String id) {
        if (searchRepository.existsById(id)) {
            searchRepository.deleteById(id);
            System.out.println("deleted");
        }
    }
}
