package uk.co.rpl.demo1.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.co.rpl.demo1.Configurator;
import uk.co.rpl.demo1.User;

/**
 *
 * @author philip
 */
@Slf4j
@Service
@AllArgsConstructor
public class DemoAccessService {
    private final Configurator conf;
    private final RestTemplate template;

    public User getMainUser(){
        var url = conf.getBase()+"/main-user";
        log.debug("Get main user calling url {}", url);
        var user = template.getForEntity(url, User.class);
        return user.getBody();
    }
}
