package uz.pdp.cityfront.service.apartment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.apartment.FlatDto;
import uz.pdp.cityfront.domain.dto.apartment.ReadFromJs;
import uz.pdp.cityfront.domain.dto.filter.Filter;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlatService {
    private final RestTemplate restTemplate;
    @Value("${services.user-service}")
    private String url;
    public List<FlatDto> getFlats(UUID accId,String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/flat/" + accId + "/accommodation");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<FlatDto> entity = new HttpEntity<>(headers);
        ResponseEntity<List<FlatDto>> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
        return exchange.getBody();
    }
    public List<FlatDto> search(Filter filter, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/flat/all");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<Filter> entity = new HttpEntity<>(filter,headers);
        ResponseEntity<List<FlatDto>> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, new ParameterizedTypeReference<>() {
        });
        return exchange.getBody();
    }
    public List<FlatDto> getUsers(UUID userId,String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/flat/gt");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<FlatDto>>() {
        }).getBody();
    }

    public FlatDto getFlat(UUID id,String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/flat/get/" + id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<FlatDto> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,FlatDto.class).getBody();
    }

    public List<FlatDto> getFlatsByType(String type, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/flat/all");
        type = "{\"type\": \"" + type + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(type,headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, new ParameterizedTypeReference<List<FlatDto>>() {
        }).getBody();
    }
}
