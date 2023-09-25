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
import uz.pdp.cityfront.domain.dto.card.CardReadDto;
import uz.pdp.cityfront.domain.entity.token.JwtTokenEntity;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.repository.JwtTokenRepository;
import uz.pdp.cityfront.service.user.UserService;

import java.security.Principal;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final JwtTokenRepository jwtTokenRepository;
    @Value("${services.user-service}")
    private String userUrl;

    public CardReadDto save(CardReadDto createCardDto,Principal principal) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(userUrl + "/api/v1/payment/save");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CardReadDto> entity = new HttpEntity<>(createCardDto, headers);

        JwtTokenEntity token = jwtTokenRepository.findJwtTokenEntitiesByUsername(principal.getName()).orElseThrow(() -> new MyException("Jwt not found!"));
        headers.set("authorization","Bearer " + token.getToken());

        try {
            return restTemplate.exchange(uri.toUriString(),HttpMethod.POST,entity,CardReadDto.class).getBody();
        }catch (RuntimeException e){
            throw  new RuntimeException(e.getMessage());
        }
    }
}
