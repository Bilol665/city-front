package uz.pdp.cityfront.controller.home;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.exceptions.MyException;
import uz.pdp.cityfront.service.user.JwtService;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final JwtService jwtService;
    @GetMapping("/")
    public String index(
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ) {
        try {
            String token = Utils.getCookie("token", request);
            String email = Utils.getCookie("email", request);
            UserReadDto user = userService.getUserByUsername(email);
            jwtService.checkTokenExpiration(token);
            model.addAttribute("user", user);
            model.addAttribute("role", userService.getRole(user));
            response.addCookie(Utils.createCookie("token",token));
            response.addCookie(Utils.createCookie("email",email));
            return "menu";
        } catch (MyException | ExpiredJwtException ex) {
            return "session-expired";
        } catch (Exception e) {
            return "index";
        }
    }
    @RequestMapping(value = "/log",method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @GetMapping("/home" )
    public String home(
            Model model,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        response.addCookie(Utils.createCookie("token", token));
        response.addCookie(Utils.createCookie("email", email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user", user);
        model.addAttribute("role", userService.getRole(user));
        return "menu";
    }
    @RequestMapping(value = "/inbox",method = RequestMethod.GET)
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
        model.addAttribute("user", user);
        model.addAttribute("role", userService.getRole(user));
        return "menu";
    }
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Utils.removeCookies(request,response);
        return "index";
    }
}
