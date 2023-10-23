package uz.pdp.cityfront.domain.dto.apartment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AccommodationCreateDto {
    @NotBlank(message = "Name must be present")
    private String name;
    @NotNull(message = "Company id cannot be null")
    private UUID companyId;
    private LocationDto locationEntity;
}
