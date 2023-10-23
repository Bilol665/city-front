package uz.pdp.cityfront.controller.apartment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.pdp.cityfront.domain.dto.apartment.AccommodationCreateDto;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.service.apartment.AccommodationService;
import uz.pdp.cityfront.service.apartment.CompanyService;
import uz.pdp.cityfront.service.apartment.FlatService;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/apartment")
public class AccommodationController {
    private final AccommodationService accommodationService;
    private final CompanyService companyService;
    private final FlatService flatService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    @RequestMapping(path = "/add/premium",method = RequestMethod.POST)
    public String addPremium(
            AccommodationCreateDto accommodationCreateDto,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        accommodationService.addPremium(accommodationCreateDto,token);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        model.addAttribute("replace",true);
        model.addAttribute("accommodations",accommodationService.search(new Filter(),token));
        try {
            model.addAttribute("companies",objectMapper.writeValueAsString(companyService.getCompanyByOwner(user.getId(),token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "/apartment/accommodations";
    }
    @RequestMapping(path = "/add/eco",method = RequestMethod.POST)
    public String addEco(
            AccommodationCreateDto accommodationCreateDto,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        accommodationService.addEco(accommodationCreateDto,token);
        model.addAttribute("replace",true);
        model.addAttribute("accommodations",accommodationService.search(new Filter(),token));
        try {
            model.addAttribute("companies",objectMapper.writeValueAsString(companyService.getCompanyByOwner(user.getId(),token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "/apartment/accommodations";
    }

    @RequestMapping(path = "/search",method = RequestMethod.POST)
    public String search(
            Filter filter,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        String accessToken = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("accommodations",accommodationService.search(filter,accessToken));
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        response.addCookie(Utils.createCookie("token",accessToken));
        response.addCookie(Utils.createCookie("email",email));
        try {
            model.addAttribute("companies",objectMapper.writeValueAsString(companyService.getCompanyByOwner(user.getId(),accessToken)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "/apartment/accommodations";
    }
    @RequestMapping(path = "/get/acc/{accId}",method = RequestMethod.GET)
    public String getAcc(
            Model model,
            HttpServletRequest request,
            @PathVariable UUID accId,
            HttpServletResponse response
    ){
        String accessToken = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("accommodation",accommodationService.getAcc(accId,accessToken));
        model.addAttribute("flats",flatService.getFlats(accId,accessToken));
        model.addAttribute("block",false);
        response.addCookie(Utils.createCookie("token",accessToken));
        response.addCookie(Utils.createCookie("email",email));
        return "/apartment/accDetails";
    }
    @RequestMapping(path = "/getAll",method = RequestMethod.GET)
    public String getAll(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        String token = Utils.getCookie("token",request);
        String email = Utils.getCookie("email",request);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role", userService.getRole(user));
        model.addAttribute("accommodations", accommodationService.search(new Filter(),token));
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        try {
            model.addAttribute("companies",objectMapper.writeValueAsString(companyService.getCompanyByOwner(user.getId(),token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "/apartment/accommodations";
    }
    @RequestMapping(path = "/apartments/{userId}",method = RequestMethod.GET)
    public String getUserApartments(
            @PathVariable UUID userId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("accommodations",accommodationService.getUserAcc(userId,token));
        try {
            model.addAttribute("companies",objectMapper.writeValueAsString(companyService.getCompanyByOwner(user.getId(),token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "apartment/accommodations";
    }
}
