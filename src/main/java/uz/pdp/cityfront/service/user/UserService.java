package uz.pdp.cityfront.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.reader.JwtResponse;
import uz.pdp.cityfront.domain.dto.response.ApiResponse;
import uz.pdp.cityfront.domain.dto.response.ApiResponse4Jwt;
import uz.pdp.cityfront.domain.dto.user.*;
import uz.pdp.cityfront.domain.entity.token.JwtTokenEntity;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.repository.JwtTokenRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final RestTemplate restTemplate;
    private final JwtTokenRepository jwtTokenRepository;
    @Value("${services.user-service}")
    private String userServiceUrl;

    public JwtTokenEntity login(LoginDto loginDto) {
        if((loginDto.getEmail() == null || loginDto.getEmail().isBlank())
                && (loginDto.getPassword() == null || loginDto.getPassword().isBlank())) {
            throw new MyException("Email and password is missing!");
        } else if (loginDto.getEmail() == null || loginDto.getEmail().isBlank()) {
            throw new MyException("Email is missing!");
        }else if (loginDto.getPassword() == null || loginDto.getPassword().isBlank()) {
            throw new MyException("Password is missing!");
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/auth/login");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto,headers);
        try {
            JwtResponse body = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, JwtResponse.class).getBody();
            assert body != null;
            JwtTokenEntity token1 = jwtTokenRepository.findJwtTokenEntitiesByUsername(loginDto.getEmail()).orElse(null);
            JwtTokenEntity token = JwtTokenEntity.builder()
                    .id(token1 == null ? UUID.randomUUID() : token1.getId())
                    .username(loginDto.getEmail())
                    .token(body.getAccessToken())
                    .build();
            return jwtTokenRepository.save(token);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public UserReadDto signUp(UserRequestDto userRequestDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/auth/sign-up");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRequestDto> entity = new HttpEntity<>(userRequestDto,headers);
        try{
            return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, UserReadDto.class).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public JwtTokenEntity verify(VerificationDto verificationDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/auth/verify/"
                + verificationDto.getUserId()
                + "/" + verificationDto.getCode());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ApiResponse4Jwt> entity = new HttpEntity<>(headers);
        ApiResponse4Jwt response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, ApiResponse4Jwt.class).getBody();
        UserReadDto user = getUserById(UUID.fromString(verificationDto.getUserId()));
        assert response != null;
        JwtResponse data = response.getData();
        JwtTokenEntity token = jwtTokenRepository.findJwtTokenEntitiesByUsername(user.getEmail()).orElse(null);
        if(token == null) return jwtTokenRepository.save(JwtTokenEntity.builder().token(data.getAccessToken()).username(user.getEmail()).build());
        else return jwtTokenRepository.save(JwtTokenEntity.builder().id(token.getId()).username(user.getEmail()).token(data.getAccessToken()).build());
    }
    public UserReadDto getUserById(UUID id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/get/id")
                .queryParam("id", id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, UserReadDto.class).getBody();
    }

    public void sendReset(String email) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/user/api/v1/auth/reset-password/" + email);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, JwtResponse.class);
    }

    public Map<String,String> passwordReset(String email, Integer id) {
        if(id != 1) throw new MyException("Not your link!");
        Map<String, String> map = new HashMap<>();
        map.put("email",email);
        map.put("path","/auth/passwordReset");
        return map;
    }

    public void changePassword(String email, ResetPasswordDto password,String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUrl + "/api/v1/edit/changePassword/" + email);
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization","Bearer "+token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ResetPasswordDto> entity = new HttpEntity<>(password,headers);
        restTemplate.exchange(builder.toUriString(),HttpMethod.PUT,entity, ApiResponse.class);
    }


}
