package uk.co.rpl.demo1;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author philip
 */
@Configuration
@Getter
@Slf4j
public class Configurator {
    @Value("${api-conf-base}")
    private String base;
    @Autowired
    private RestTemplateBuilder templateBuilder;

    @Bean
    public RestTemplate getRestTemplate(){
        log.debug("Generating template builder");
        return templateBuilder.build();
    }
}
