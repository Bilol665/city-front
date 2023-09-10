package uz.pdp.cityfront.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class VerificationDto {
    private UUID userId;
    private String code;
}
