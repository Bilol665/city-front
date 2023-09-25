package uz.pdp.cityfront.domain.dto.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccommodationReadDto {
    private String name;
    private LocationDto locationEntity;
    private Integer numberOfFlats;
    private Integer floors;
    private CompanyDto company;
}
