package de.bsc_projekt.cloneflix.Controller;

import de.bsc_projekt.cloneflix.Models.ElasticModels.Title;
import de.bsc_projekt.cloneflix.Repositories.Elasticsearch.SearchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/search")
public class SearchController {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchController(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @GetMapping(value = "/{search}")
    public List<Title> findAllTitlesByDescription(@PathVariable String search, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 12);
        return searchRepository.searchForMatchingTitle(search, pageable);
    }
}
