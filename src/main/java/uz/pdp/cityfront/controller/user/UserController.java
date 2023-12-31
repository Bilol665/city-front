package uz.pdp.cityfront.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public String getUser(
            @PathVariable UUID userId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",userService.getUserById(userId));
        model.addAttribute("role",userService.getRole(user));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        return "/user/userDetails";
    }
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public String getAll(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("users",userService.getAll(token));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        return "/user/users";
    }
    @RequestMapping(value = "/block/{userId}",method = RequestMethod.GET)
    public String block(
            @PathVariable UUID userId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("users",userService.getAll(token));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        userService.block(userId,token);
        return "user/users";
    }
    @RequestMapping(value = "/unblock/{userId}",method = RequestMethod.GET)
    public String unblock(
            @PathVariable UUID userId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("users",userService.getAll(token));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        userService.unblock(userId,token);
        return "user/users";
    }
}
