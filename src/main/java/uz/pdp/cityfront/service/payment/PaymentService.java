package uz.pdp.cityfront.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.reader.CardReadDto;
import uz.pdp.cityfront.domain.dto.reader.CreateCardDto;
import uz.pdp.cityfront.domain.dto.reader.JwtResponse;
import uz.pdp.cityfront.domain.dto.reader.UserReadDto;
import uz.pdp.cityfront.domain.entity.token.JwtTokenEntity;
import uz.pdp.cityfront.repository.JwtTokenRepository;
import uz.pdp.cityfront.service.user.UserService;

import java.security.Principal;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final JwtTokenRepository jwtTokenRepository;
    @Value("${services.user-service}")
    private String userUrl;

    public CardReadDto save(CreateCardDto createCardDto) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(userUrl + "/api/v1/payment/save");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateCardDto> entity = new HttpEntity<>(createCardDto, headers);

        headers.set("authorization","Bearer " +"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWxzaG9kb3ZmYXpsaWRkaW5AZ21haWwuY29tIiwiaWF0IjoxNjk0NzgxMjc3LCJleHAiOjE2OTQ4MTEyNzcsInJvbGVzIjpbIlJPTEVfVVNFUiJdfQ.E1xNoUCxzm-z7lQvRBovjTTRJ4ivORfJSQ0i1ImadbUX8aNqIksGrokxBnF0lPy9wkDv9rE768Dbetxhn158Sw");

        try {
            return restTemplate.exchange(uri.toUriString(),HttpMethod.POST,entity,CardReadDto.class).getBody();
        }catch (RuntimeException e){
            throw  new RuntimeException(e.getMessage());
        }
    }
}
