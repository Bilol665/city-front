package uz.pdp.cityfront.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.service.user.UserInboxService;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inbox")
public class InboxController {
    private final UserService userService;
    private final UserInboxService userInboxService;
    @RequestMapping(method = RequestMethod.GET)
    public String inbox(
            Model model,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        response.addCookie(Utils.createCookie("token", token));
        response.addCookie(Utils.createCookie("email", email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("inboxes",userInboxService.getUserInbox(user,token));
        model.addAttribute("user", user);
        model.addAttribute("role", userService.getRole(user));
        return "user/inbox";
    }
    @RequestMapping(path = "/{inboxId}",method = RequestMethod.GET)
    public String inboxDetails(
            @PathVariable UUID inboxId,
            @CookieValue(name = "token") String token,
            @CookieValue(name = "email") String email,
            HttpServletResponse response,
            Model model
    ) {
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("inbox",userInboxService.getInboxDetails(inboxId,token));
        return "user/inboxDetails";
    }
    @RequestMapping(path = "/approve/{inboxId}",method = RequestMethod.GET)
    public String approve(
            @PathVariable UUID inboxId,
            @CookieValue(name = "token") String token,
            @CookieValue(name = "email") String email,
            Model model,
            HttpServletResponse response
    ) {
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("message",userInboxService.approve(inboxId,token).getMessage());
        model.addAttribute("inbox",userInboxService.getInboxDetails(inboxId,token));
        return "user/inboxDetails";
    }
    @RequestMapping(path = "/reject/{inboxId}",method = RequestMethod.GET)
    public String reject(
            @PathVariable UUID inboxId,
            @CookieValue(name = "token") String token,
            @CookieValue(name = "email") String email,
            Model model,
            HttpServletResponse response
    ) {
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("message",userInboxService.reject(inboxId,token).getMessage());
        model.addAttribute("inbox",userInboxService.getInboxDetails(inboxId,token));
        return "user/inboxDetails";
    }
    @RequestMapping(path = "/purchase/{inboxId}/approve",method = RequestMethod.GET)
    public String approvePurchase(
            @PathVariable UUID inboxId,
            @RequestParam(name = "cardNumber") String cardNumber,
            @CookieValue(name = "token") String token,
            @CookieValue(name = "email") String email,
            Model model,
            HttpServletResponse response
    ) {
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("message",userInboxService.approvePurchase(inboxId,token,cardNumber).getMessage());
        model.addAttribute("inbox",userInboxService.getInboxDetails(inboxId,token));
        return "user/inboxDetails";
    }
    @RequestMapping(path = "/purchase/{inboxId}/reject",method = RequestMethod.GET)
    public String rejectPurchase(
            @PathVariable UUID inboxId,
            @CookieValue(name = "token") String token,
            @CookieValue(name = "email") String email,
            Model model,
            HttpServletResponse response
    ) {
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("message",userInboxService.rejectPurchase(inboxId,token).getMessage());
        model.addAttribute("inbox",userInboxService.getInboxDetails(inboxId,token));
        return "user/inboxDetails";
    }
}
