package uz.pdp.cityfront.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.LoginDto;
import uz.pdp.cityfront.domain.dto.UserRequestDto;
import uz.pdp.cityfront.domain.dto.VerificationDto;
import uz.pdp.cityfront.domain.dto.reader.JwtResponse;
import uz.pdp.cityfront.domain.dto.reader.UserReadDto;
import uz.pdp.cityfront.domain.dto.response.ApiResponse4Jwt;
import uz.pdp.cityfront.domain.entity.token.JwtTokenEntity;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.repository.JwtTokenRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate restTemplate;
    private final JwtTokenRepository jwtTokenRepository;
    @Value("${services.user-service}")
    private String userServiceUrl;

    public void login(LoginDto loginDto) {
        if((loginDto.getEmail() == null || loginDto.getEmail().isBlank())
                && (loginDto.getPassword() == null || loginDto.getPassword().isBlank())) {
            throw new MyException("Email and password is missing!");
        } else if (loginDto.getEmail() == null || loginDto.getEmail().isBlank()) {
            throw new MyException("Email is missing!");
        }else if (loginDto.getPassword() == null || loginDto.getPassword().isBlank()) {
            throw new MyException("Password is missing!");
        }
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

    public UserReadDto signUp(UserRequestDto userRequestDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/api/v1/auth/sign-up");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRequestDto> entity = new HttpEntity<>(userRequestDto,headers);
        try{
            return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, UserReadDto.class).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void verify(VerificationDto verificationDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/api/v1/auth/verify/"
                + verificationDto.getUserId()
                + "/" + verificationDto.getCode());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ApiResponse4Jwt> entity = new HttpEntity<>(headers);
        ApiResponse4Jwt response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, ApiResponse4Jwt.class).getBody();
        UserReadDto user = getUserById(verificationDto.getUserId());
        assert response != null;
        jwtTokenRepository.save(JwtTokenEntity.builder().username(user.getEmail()).token(response.getData().getAccessToken()).build());
    }
    public UserReadDto getUserById(UUID id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/api/v1/get/id")
                .queryParam("id", id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, UserReadDto.class).getBody();
    }
    private JwtResponse refreshJwt(LoginDto loginDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/api/v1/auth/login");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto, headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, JwtResponse.class).getBody();
    }
}
