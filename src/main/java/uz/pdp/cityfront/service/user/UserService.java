package uz.pdp.cityfront.service.user;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.LoginDto;
import uz.pdp.cityfront.domain.dto.UserRequestDto;
import uz.pdp.cityfront.domain.dto.reader.JwtResponse;
import uz.pdp.cityfront.domain.entity.token.JwtTokenEntity;
import uz.pdp.cityfront.repository.JwtTokenRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate restTemplate;
    private final JwtTokenRepository jwtTokenRepository;
    @Value("${services.user-service}")
    private String userServiceUrl;

    public void login(LoginDto loginDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/api/v1/auth/login");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto,headers);
        try {
            JwtResponse body = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, JwtResponse.class).getBody();
            assert body != null;
            JwtTokenEntity token = JwtTokenEntity.builder()
                    .username(loginDto.getEmail())
                    .token(body.getAccessToken())
                    .build();
            jwtTokenRepository.save(token);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void signUp(UserRequestDto userRequestDto, BindingResult bindingResult) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/api/v1/auth/sign-up");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRequestDto> entity = new HttpEntity<>(userRequestDto,headers);
        try{
            System.out.println(restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, JsonNode.class));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
