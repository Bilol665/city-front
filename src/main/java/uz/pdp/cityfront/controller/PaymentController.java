package uz.pdp.cityfront.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityfront.domain.dto.card.CardReadDto;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.service.payment.PaymentService;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

import java.security.Principal;
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
        UserReadDto user = userService.getUserById(id).getDetails();
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
             CardReadDto createCardDto,
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
        UserReadDto user = userService.getUserById(id).getDetails();
        model.addAttribute("user",user);
        return "card/cardMenu";
    }
    @RequestMapping(value = "/card/{id}", method = RequestMethod.GET)
    public String card(
            @PathVariable UUID id,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("card",paymentService.getCard(id,token));
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        return "card/cardDetails";
    }
}
