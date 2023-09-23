package uz.pdp.cityfront.domain.dto.reader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardReadDto {
    private String number;
    private String holderName;
    private Integer month;
    private Integer year;
    private Integer pinCode;
    private String type;
}
