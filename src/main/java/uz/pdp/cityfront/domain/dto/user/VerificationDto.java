package uz.pdp.cityfront.domain.dto.user;

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
    private String userId;
    private String code;
}
