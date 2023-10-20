package uz.pdp.cityfront.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.domain.dto.response.ApiResponse;
import uz.pdp.cityfront.domain.dto.user.InboxReadDto;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.repository.JwtTokenRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInboxService {
    private final RestTemplate restTemplate;
    private final JwtTokenRepository jwtTokenRepository;
    private final ModelMapper modelMapper;
    @Value("${services.user-service}")
    private String userServiceUrl;

    public List<InboxReadDto> getUserInbox(UserReadDto user, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/inbox/get/" + user.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<Filter> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<InboxReadDto>>() {
        }).getBody();
    }

    public InboxReadDto getInboxDetails(UUID inboxId, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/inbox/" + inboxId + "/get");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity, InboxReadDto.class).getBody();
    }

    public ApiResponse approve(UUID inboxId, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/inbox/" + inboxId + "/approve");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(),HttpMethod.PUT,entity,ApiResponse.class).getBody();
    }

    public ApiResponse reject(UUID inboxId, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/inbox/" + inboxId + "/reject");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(),HttpMethod.PUT,entity,ApiResponse.class).getBody();
    }

    public ApiResponse approvePurchase(UUID inboxId, String token, String cardNumber) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/inbox/purchase/" + inboxId + "/approve")
                .queryParam("cardNumber",cardNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(),HttpMethod.PUT,entity,ApiResponse.class).getBody();
    }

    public ApiResponse rejectPurchase(UUID inboxId, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/inbox/purchase/" + inboxId + "/reject");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(),HttpMethod.PUT,entity,ApiResponse.class).getBody();
    }
}
