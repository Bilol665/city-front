package uz.pdp.cityfront.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityfront.domain.dto.LoginDto;
import uz.pdp.cityfront.domain.dto.ResetPasswordDto;
import uz.pdp.cityfront.domain.dto.UserRequestDto;
import uz.pdp.cityfront.domain.dto.VerificationDto;
import uz.pdp.cityfront.domain.dto.reader.UserReadDto;
import uz.pdp.cityfront.domain.entity.token.JwtTokenEntity;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.service.user.UserService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    @PostMapping("/login" )
    public String login(
            LoginDto loginDto,
            Model model
    ) {
        try {
            JwtTokenEntity login = userService.login(loginDto);
            model.addAttribute("email",loginDto.getEmail());
            model.addAttribute("token",login.getToken());
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
            Model model
    ) {
        try {
            JwtTokenEntity verify = userService.verify(verificationDto);
            model.addAttribute("email",verify.getUsername());
            model.addAttribute("token",verify.getToken());
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
            Model model
    ) {
        try {
            userService.changePassword(resetPasswordDto.getEmail(),resetPasswordDto,resetPasswordDto.getToken());
            model.addAttribute("token",resetPasswordDto.getToken());
        }catch (Exception e){
            return "index_password_error";
        }
        return "index";
    }

    @GetMapping("/getPage/changePassword/{email}/{token}")
    public String getPagePassword(
            @PathVariable String email,
            @PathVariable String token,
            Model model
    ){
        model.addAttribute("email",email);
        model.addAttribute("token",token);
        return "changePassword";
    }

}
