package uz.pdp.cityfront.domain.dto.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginDto {
    private String email;
    private String password;
}
