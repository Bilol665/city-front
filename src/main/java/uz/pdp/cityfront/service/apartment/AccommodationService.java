package uz.pdp.cityfront.service.apartment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.apartment.AccommodationReadDto;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.repository.JwtTokenRepository;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccommodationService {
    private final RestTemplate restTemplate;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserService userService;
    @Value("${services.user-service}")
    private String apartmentURL;

    public List<AccommodationReadDto> search(Filter filter, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apartmentURL + "/apartment/api/v1/accommodation/get/all");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        HttpEntity<Filter> entity = new HttpEntity<>(filter, headers);
        ResponseEntity<List<AccommodationReadDto>> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<AccommodationReadDto>>() {
        });
        return exchange.getBody();
    }

    public AccommodationReadDto getAcc(UUID accId, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apartmentURL + "/apartment/api/v1/accommodation/get/byId/" + accId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, AccommodationReadDto.class).getBody();
        } catch (Exception e) {
            Utils.checkHttpStatus(e.getMessage());
            throw new MyException(e.getMessage());
        }
    }

    public List<AccommodationReadDto> getUserAcc(UUID userId, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apartmentURL + "/apartment/api/v1/accommodation/get/" + userId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<AccommodationReadDto>>() {
        }).getBody();
    }
}
