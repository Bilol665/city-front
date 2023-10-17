package uz.pdp.cityfront.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserForHtml {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String state;
    private int attempts;
}
