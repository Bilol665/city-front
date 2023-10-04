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
    public List<FlatDto> search(ReadFromJs read, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/flat/all");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        Filter filter = new Filter();
        filter.set(read.getCategory(),read.getSearchValue());
        HttpEntity<Filter> entity = new HttpEntity<>(filter,headers);
        ResponseEntity<List<FlatDto>> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, new ParameterizedTypeReference<>() {
        });
        return exchange.getBody();
    }
}
