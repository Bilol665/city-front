package uz.pdp.cityfront.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uz.pdp.cityfront.domain.dto.reader.CardReadDto;
import uz.pdp.cityfront.domain.dto.reader.CreateCardDto;
import uz.pdp.cityfront.domain.dto.reader.UserReadDto;
import uz.pdp.cityfront.service.payment.PaymentService;
import uz.pdp.cityfront.service.user.UserService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor

public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;

    @GetMapping("/menu/{id}")
    public String menu(
            @PathVariable UUID id,
            Model model
    ){
        UserReadDto user = userService.getUserById(id);
        List<CardReadDto>cards= paymentService.getCardUser(user.getId());
            model.addAttribute("cards",cards);
            model.addAttribute("id",user.getId());
        return "menu";
    }
    @GetMapping("/saveCard")
    public String save() {
        return "/card/cardSave";
    }
    @PostMapping("/saveCard")
    public String save(
            CreateCardDto createCardDto,
            Principal principal
    ){
    paymentService.save(createCardDto,principal);
    return "index";
    }

    @GetMapping("/cardPage/{id}")
    public String pageCard(
        @PathVariable UUID id,
        Model model
    ){
        UserReadDto user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "card/cardMenu";
    }




}
