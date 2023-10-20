package uz.pdp.cityfront.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.pdp.cityfront.domain.dto.booking.BookFlatDto;
import uz.pdp.cityfront.domain.dto.response.ApiResponse;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final RestTemplate restTemplate;
    @Value("${services.user-service}")
    private String url;

    public String book(BookFlatDto bookFlatDto, String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/booking/book/flat/" + bookFlatDto.getFlatId())
                .queryParam("cardNumber",bookFlatDto.getCardNumber());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ApiResponse> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, ApiResponse.class);
        if(exchange.getStatusCode().isSameCodeAs(HttpStatus.OK)) return "Successfully booked!";
        return "Booking failed";
    }

    public String buyFlat(BookFlatDto bookFlatDto, String token,String cardNumber) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/booking/buy/flat/" + bookFlatDto.getFlatId())
                .queryParam("cardNumber",cardNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer " + token);
        HttpEntity<BookFlatDto> entity = new HttpEntity<>(headers);
        ResponseEntity<ApiResponse> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, ApiResponse.class);
        if(exchange.getStatusCode().isSameCodeAs(HttpStatus.OK)) return "Successfully bought";
        return "Purchase failed";
    }
}
