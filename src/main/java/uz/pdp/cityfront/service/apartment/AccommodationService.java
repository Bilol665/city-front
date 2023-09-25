package uz.pdp.cityfront.service.apartment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.apartment.AccommodationReadDto;
import uz.pdp.cityfront.domain.dto.card.CardReadDto;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.repository.JwtTokenRepository;
import uz.pdp.cityfront.service.user.UserService;

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
    public List<AccommodationReadDto> search(Filter filter, UUID userId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apartmentURL + "/apartment/api/v1/accommodation/get/all");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization",jwtTokenRepository.findJwtTokenEntitiesByUsername(userService.getUserById(userId).getName())
                .orElseThrow(() -> new MyException("Jwt not found!")).getToken());
        HttpEntity<Filter> entity = new HttpEntity<>(filter,headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<AccommodationReadDto>>() {
        }).getBody();
    }
}
