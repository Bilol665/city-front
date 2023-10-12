package uz.pdp.cityfront.controller.apartment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityfront.domain.dto.apartment.ReadFromJs;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.service.apartment.AccommodationService;
import uz.pdp.cityfront.service.apartment.FlatService;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/flat")
public class FlatController {
    private final FlatService flatService;
    private final AccommodationService accommodationService;
    private final UserService userService;

    @RequestMapping(value = "/acc/{accId}",method = RequestMethod.GET)
    public String getAccommodations(
            @PathVariable UUID accId,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        model.addAttribute("flats",flatService.getFlats(accId,token));
        model.addAttribute("accommodation",accommodationService.getAcc(accId,token));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        return "/apartment/flats";
    }
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(
            Filter filter,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("flats",flatService.search(filter,token));
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        return "/apartment/flats";
    }
    @RequestMapping(value = "/{flatId}",method = RequestMethod.GET)
    public String getFlat(
            @PathVariable UUID flatId,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ) {
        String token = Utils.getCookie("token",request);
        String email = Utils.getCookie("email",request);
        model.addAttribute("flat",flatService.getFlat(flatId,token));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        return "/apartment/flatDetails";
    }
    @RequestMapping(value = "/byType/{type}",method = RequestMethod.GET)
    public String getFlatsByType(
            @PathVariable String type,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ) {
        String token = Utils.getCookie("token",request);
        String email = Utils.getCookie("email",request);
        model.addAttribute("flats",flatService.getFlatsByType(type,token));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("user",user);
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        return "/apartment/flats";
    }
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public String getAll(
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("flats",flatService.search(new Filter(),token));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        return "/apartment/flats";
    }
}
