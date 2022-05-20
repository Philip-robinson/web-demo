/*
 * 
 * 
 */
package uk.co.rpl.webdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author philip
 */
@Data
@ToString
public class User {

    @JsonProperty("firstName")
    private String forename;
    @JsonProperty("lastName")
    private String surname;

}
