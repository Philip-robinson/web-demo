package uk.co.rpl.webdemo.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.co.rpl.webdemo.Configurator;
import uk.co.rpl.webdemo.User;

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

    public List<User> getAllUsers(){
        try{
        var url = conf.getBase()+"/users";
        log.debug("Get all user calling url {}", url);
        ParameterizedTypeReference<List<User>> respType =
                new ParameterizedTypeReference<List<User>>(){};
        var req = new RequestEntity(HttpMethod.GET, new URI(url));
        var users = template.exchange(req, respType);
        return users.getBody();
        } catch (URISyntaxException ex) {
            log.error("Failed to read users: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
}
