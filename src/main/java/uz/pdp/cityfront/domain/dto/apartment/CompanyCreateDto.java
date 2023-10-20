package uz.pdp.cityfront.domain.dto.apartment;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CompanyCreateDto {
    private String name;
    private String description;
    private Double balance;
    private UUID cardId;
}
