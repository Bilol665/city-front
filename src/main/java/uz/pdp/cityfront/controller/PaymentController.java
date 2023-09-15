package uz.pdp.cityfront.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.cityfront.domain.dto.reader.CreateCardDto;
import uz.pdp.cityfront.service.payment.PaymentService;

import java.security.Principal;
import java.util.UUID;

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
             CreateCardDto createCardDto
    ){
    paymentService.save(createCardDto);
    return "MainPage";
    }
}
