package uz.pdp.cityfront.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.cityfront.domain.dto.LoginDto;
import uz.pdp.cityfront.domain.dto.UserRequestDto;
import uz.pdp.cityfront.service.user.UserService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/login")
    public String login(
            LoginDto loginDto
    ) {
        userService.login(loginDto);
        return "MainPage";
    }
    @GetMapping ("/sign-up")
    public String signUp() {
        return "/auth/signUp";
    }
    @PostMapping("/sign-up")
    public String signUp(UserRequestDto userRequestDto, BindingResult bindingResult){
        userService.signUp(userRequestDto,bindingResult);
        return "MainPage";
    }
}
