package uz.pdp.cityfront.service.apartment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.apartment.CompanyCreateDto;
import uz.pdp.cityfront.domain.dto.apartment.CompanyDto;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.service.payment.PaymentService;
import uz.pdp.cityfront.util.Utils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final RestTemplate restTemplate;
    private final PaymentService paymentService;
    @Value("${services.user-service}")
    private String url;
    public CompanyDto getCompany(UUID id,String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/company/get/" + id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<CompanyDto> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CompanyDto.class);
            return exchange.getBody();
        } catch (Exception e) {
            log.warn("Error at CompanyService getCompany -> {}",e.getMessage());
            throw new MyException(e.getMessage());
        }
    }

    public List<CompanyDto> getCompanyByOwner(UUID id, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/company/" + id + "/get");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<CompanyDto>>() {}).getBody();
    }
    public List<CompanyDto> getAllCompanies(Filter filter,String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/company/all");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<Filter> entity = new HttpEntity<>(filter,headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<CompanyDto>>() {}).getBody();
    }

    public CompanyDto addNewCompany(CompanyCreateDto companyCreateDto, String token) {
        companyCreateDto.setBalance(paymentService.getCard(companyCreateDto.getCardId(),token).getBalance());
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/apartment/api/v1/company/add");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<CompanyCreateDto> entity = new HttpEntity<>(companyCreateDto,headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, CompanyDto.class).getBody();
    }
}
