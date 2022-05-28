/*
 *
 *
 */
package uk.co.rpl.webdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author philip
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @JsonProperty("firstName")
    private String forename;
    @JsonProperty("lastName")
    private String surname;

}
