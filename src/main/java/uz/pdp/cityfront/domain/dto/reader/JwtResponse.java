package uz.pdp.cityfront.domain.dto.reader;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {
    String accessToken;
    String refreshToken;
}
