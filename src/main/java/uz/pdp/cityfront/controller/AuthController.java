package uz.pdp.cityfront.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityfront.domain.dto.user.LoginDto;
import uz.pdp.cityfront.domain.dto.user.ResetPasswordDto;
import uz.pdp.cityfront.domain.dto.user.UserRequestDto;
import uz.pdp.cityfront.domain.dto.user.VerificationDto;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.domain.entity.token.JwtTokenEntity;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    @PostMapping("/login" )
    public String login(
            LoginDto loginDto,
            Model model,
            HttpServletResponse response
    ) {
        try {
            JwtTokenEntity login = userService.login(loginDto);
            response.addCookie(Utils.createCookie("token",login.getToken()));
            response.addCookie(Utils.createCookie("email",loginDto.getEmail()));
            model.addAttribute("user",userService.getUserByUsername(login.getUsername()));
            model.addAttribute("role",userService.getRole(userService.getUserByUsername(login.getUsername())));
        } catch (MyException e) {
            model.addAttribute("message",e.getMessage());
            return "index_password_error";
        }
        return "menu";
    }

    @PostMapping("/register")
    public String signUp(
            @ModelAttribute UserRequestDto userRequestDto,
            Model model
    ){
        try {
            UserReadDto userReadDto = userService.signUp(userRequestDto);
            model.addAttribute("userId",userReadDto.getId());
            return "verification";
        }catch (Exception e){
            return "index_email_error";
        }

    }
    @PostMapping("/verify")
    public String verify(
            @ModelAttribute VerificationDto verificationDto,
            Model model,
            HttpServletResponse response
    ) {
        try {
            JwtTokenEntity verify = userService.verify(verificationDto);
            response.addCookie(Utils.createCookie("token",verify.getToken()));
            model.addAttribute("email",verify.getUsername());
            model.addAttribute("user",userService.getUserByUsername(verify.getUsername()));
            model.addAttribute("role",userService.getRole(userService.getUserByUsername(verify.getUsername())));
        }catch (Exception e){
            return "index_verification_error";
        }
        return "menu";
    }
    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "/auth/resetPassword";
    }
    @PostMapping("/resetPassword")
    public String reset(@RequestParam String email) {
        userService.sendReset(email);
        return "/auth/resetPassword";
    }
    @GetMapping("/password-reset/{email}/{id}")
    public String passwordReset(@PathVariable String email, @PathVariable Integer id,Model model) {
        Map<String, String> map = userService.passwordReset(email, id);
        model.addAttribute("email",map.get("email"));
        return map.get("path");
    }
    @PostMapping("/changePassword")
    public String password(
            ResetPasswordDto resetPasswordDto,
            @CookieValue(name = "email") String email,
            @CookieValue(name = "token") String token,
            HttpServletResponse response
    ) {
        try {
            userService.changePassword(email,resetPasswordDto,token);
            response.addCookie(Utils.createCookie("token",token));
        }catch (Exception e){
            return "index_password_error";
        }
        return "index";
    }

    @GetMapping("/getPage/changePassword")
    public String getPagePassword(
            @CookieValue(name = "email") String email,
            @CookieValue(name = "token") String token,
            HttpServletResponse response
    ){
        response.addCookie(Utils.createCookie("email",email));
        response.addCookie(Utils.createCookie("token",token));
        return "changePassword";
    }

}
