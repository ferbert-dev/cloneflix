package de.bsc_projekt.cloneflix.Models.ElasticModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * https://www.elastic.co/guide/en/elasticsearch/guide/2.x/_index_time_search_as_you_type.html
 * https://www.elastic.co/guide/en/elasticsearch/guide/2.x/_index_time_optimizations.html
 * edge n-grams for search as you type
 * https://www.elastic.co/guide/en/elasticsearch/guide/2.x/_ngrams_for_partial_matching.html
 * 
 * Der Index hat den Namen "titles" für die Titel unserers Streaming Services.
 * @setting ermöglicht es aus dem .json die Einstellungen für den Index zu setzen. In unserem Fall den custom analyzer und den standard search_analyzer.
 * Für den custom analyzer siehe den Link für Erklärungen.
 * Manche Felder wie id und posterPath sollen nicht in den Index aufgenommen werden. Das geht mit index = false.
 * 
 * Weil die Zerlegung der Suchanfrage in n-Gramme in unserem Fall ungewünschte Ergebnisse liefern kann, wird zur Laufzeit der 'standard'-Analyzer benutzt.
 * Elasticsearch funktioniert nicht mit den gettern/settern von lombok. Deswegen
 * sind die ausführlich aufgeschrieben.
 */
@Document(indexName = "titles")
@Setting(settingPath = "settings/title_index_settings.json")
public class Title {

    @Id
    @Field(type = FieldType.Text, index = false)
    private String id;

    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "standard")
    private String title;

    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "standard")
    private String[] genres;

    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "standard")
    private String[] actors;

    @Field(type = FieldType.Text, index = false)
    private String posterPath;

    @Field(type = FieldType.Text, index = false)
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Title(String id, String title, String[] genres, String[] actors, String posterPath, String model) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.actors = actors;
        this.posterPath = posterPath;
        this.model = model;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getGenres() {
        return this.genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String[] getActors() {
        return this.actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}