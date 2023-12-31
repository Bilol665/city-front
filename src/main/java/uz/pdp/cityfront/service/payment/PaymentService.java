package uz.pdp.cityfront.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.card.CardReadDto;
import uz.pdp.cityfront.domain.entity.token.JwtTokenEntity;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.repository.JwtTokenRepository;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final RestTemplate restTemplate;
    private final JwtTokenRepository jwtTokenRepository;
    @Value("${services.user-service}")
    private String userUrl;

    public CardReadDto save(CardReadDto createCardDto, Principal principal) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(userUrl + "payment/api/v1/card/save");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CardReadDto> entity = new HttpEntity<>(createCardDto, headers);

        JwtTokenEntity token = jwtTokenRepository.findJwtTokenEntitiesByUsername(principal.getName()).orElseThrow(() -> new MyException("Jwt not found!"));
        headers.set("authorization", "Bearer " + token.getToken());

        try {
            return restTemplate.exchange(uri.toUriString(), HttpMethod.POST, entity, CardReadDto.class).getBody();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<CardReadDto> getCardUser(UUID ownerId, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userUrl + "/payment/api/v1/card/" + ownerId)
                .queryParam("id", ownerId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        HttpEntity<CardReadDto> entity = new HttpEntity<>(headers);
        ResponseEntity<List<CardReadDto>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
//        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CardReadDto.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // Handle the error case, e.g., throw an exception or return an empty list
            return Collections.emptyList();
        }
    }

    public CardReadDto getCard(UUID id, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userUrl + "/payment/api/v1/card/get/" + id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CardReadDto.class).getBody();
    }
}
