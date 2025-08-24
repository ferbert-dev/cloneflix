package de.bsc_projekt.cloneflix.Repositories.Elasticsearch;

import de.bsc_projekt.cloneflix.Models.ElasticModels.Title;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<Title, String> {
    /**
     * Fuzziness is only applied to the terms of the query but not the prefix. It's
     * a tradeoff caused by using the multi_match query.
     * 
     * Fuziness causes trouble by finding and scoring a lot of titles to high... ,
     * \"fuzziness\":\"AUTO\" ist mit phrase_prefix nicht erlaubt.
     * 
     * @param search   term
     * @param pageable
     * @return List of titles that match the search term
     */
    @Query("{\"multi_match\":{\"query\":\"?0\",\"type\":\"phrase_prefix\",\"fields\":[\"title^5\",\"genres\",\"actors\"]}}")
    List<Title> searchForMatchingTitle(String search, Pageable pageable);

}
