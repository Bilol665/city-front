package uz.pdp.cityfront.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserReadDto {
    private UUID id;
    private String name;
    private String email;
    private List<RoleDto> roles;
    private String state;
    private int attempts;

}
