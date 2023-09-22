package uz.pdp.cityfront.domain.entity.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JwtTokenEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    @Column(length = 500)
    private String token;
}
