package uz.pdp.cityfront.domain.dto.reader;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateCardDto {
    private String number;
    private String holderName;
    private Integer month;
    private Integer year;
    private Integer pinCode;
    private String type;
}


