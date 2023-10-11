package uz.pdp.cityfront.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResultDto {
    private UserReadDto details;
    private Integer companies;
    private Integer apartments;
    private Integer flats;
}
