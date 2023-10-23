package uz.pdp.cityfront.domain.dto.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccommodationReadDto {
    private UUID id;
    private String name;
    private Long number;
    private LocationDto locationEntity;
    private CompanyDto company;
    private Integer numberOfFlats;
    private Integer floors;
    private String imgPath;
}
