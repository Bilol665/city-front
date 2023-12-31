package uz.pdp.cityfront.domain.dto.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.cityfront.domain.dto.card.CardReadDto;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompanyDto {
    private UUID id;
    private String name;
    private String description;
    private UserReadDto owner;
    private CardReadDto card;
}
