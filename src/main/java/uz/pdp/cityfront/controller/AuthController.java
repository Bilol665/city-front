package uz.pdp.cityfront.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.cityfront.domain.dto.LoginDto;
import uz.pdp.cityfront.domain.dto.ResetPasswordDto;
import uz.pdp.cityfront.domain.dto.UserRequestDto;
import uz.pdp.cityfront.domain.dto.VerificationDto;
import uz.pdp.cityfront.domain.dto.reader.UserReadDto;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.service.user.UserService;

import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class    AuthController {
    private final UserService userService;
    @PostMapping("/login" )
    public String login(
            LoginDto loginDto,
            Model model
    ) {
        try {
            userService.login(loginDto);
        } catch (MyException e) {
            model.addAttribute("message",e.getMessage());
            return "index";
        }
        return "MainPage";
    }
    @PostMapping("/sign-up")
    public String signUp(UserRequestDto userRequestDto, Model model){
        UserReadDto userReadDto = userService.signUp(userRequestDto);
        model.addAttribute("userId",userReadDto.getId());
        return "/auth/VerificationPage";
    }
    @GetMapping("/verify/{userId}")
    public String verifyGet(@PathVariable UUID userId,Model model)  {
        model.addAttribute("userId",userId);
        return "/auth/VerificationPage";
    }
    @PostMapping("/verify")
    public String verify(VerificationDto verificationDto) {
        userService.verify(verificationDto);
        return "MainPage";
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
    @PostMapping("/password-reset")
    public String password(@RequestParam String email, ResetPasswordDto password) {
        userService.reset(email,password);
        return "/MainPage";
    }

}
