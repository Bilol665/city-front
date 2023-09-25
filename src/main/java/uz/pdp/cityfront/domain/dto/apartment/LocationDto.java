package uz.pdp.cityfront.domain.dto.apartment;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LocationDto {
    private Double longitude;
    private Double latitude;
}