package uz.pdp.cityfront.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uz.pdp.cityfront.domain.dto.card.CardReadDto;
import uz.pdp.cityfront.service.payment.PaymentService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/menu")
    public String menu(){
        return "MainPage";
    }
    @GetMapping("/saveCard")
    public String resetPassword() {
        return "/payment/save";
    }
    @PostMapping("/saveCard")
    public String save(
             CardReadDto createCardDto,
             Principal principal
    ){
    paymentService.save(createCardDto,principal);
    return "MainPage";
    }
}
