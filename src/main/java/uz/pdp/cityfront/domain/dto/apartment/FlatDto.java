package uz.pdp.cityfront.domain.dto.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FlatDto {
    private UUID id;
    private Integer number;
    private Integer whichFloor;
    private String flatType;
    private Integer rooms;
    private UserReadDto owner;
    private String status;
    private String about;
    private Double pricePerMonth;
    private Double fullPrice;
}
