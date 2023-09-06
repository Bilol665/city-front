package uz.pdp.cityfront.domain.entity.token;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JwtTokenEntity {
    @Id
    private String username;
    private String token;
}
