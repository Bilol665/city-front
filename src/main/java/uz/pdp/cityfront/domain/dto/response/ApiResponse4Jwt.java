package uz.pdp.cityfront.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import uz.pdp.cityfront.domain.dto.reader.JwtResponse;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApiResponse4Jwt {
    private HttpStatus status;
    private boolean success;
    private String message;
    private JwtResponse data;
}
