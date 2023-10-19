package uz.pdp.cityfront.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InboxReadDto {
    private UUID id;
    private String message;
    private String state;
    private String type;
    private UserReadDto fromWhom;
    private UserReadDto toWhom;
}
