package uz.pdp.cityfront.domain.dto.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardReadDto {
    private UUID id;
    private LocalDateTime createdDate;
    private String number;
    private String holderName;
    private Date expiredDate;
    private Double balance;
    private UUID ownerId;
    private CardType type;
}
