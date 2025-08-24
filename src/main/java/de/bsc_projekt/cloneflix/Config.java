package de.bsc_projekt.cloneflix;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**://www.baeldung.com/spring-data-elasticsearch-tutorial
 *
 * Sucht Spring Data Repositories
 **/
@Configuration
@EnableElasticsearchRepositories(basePackages = "de.bsc_projekt.cloneflix.Repositories.Elasticsearch")
@ComponentScan()
public class Config {

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    String elasticsearchConnection;

    // Für die Kommunikation mit dem Elastic Server ist der Rest Client nötig
    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo(elasticsearchConnection)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
