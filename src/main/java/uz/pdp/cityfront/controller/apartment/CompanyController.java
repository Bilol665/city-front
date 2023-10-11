package uz.pdp.cityfront.controller.apartment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.pdp.cityfront.domain.dto.apartment.CompanyDto;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.service.apartment.CompanyService;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

import java.util.UUID;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final UserService userService;
    @RequestMapping(path = "/get/{id}",method = RequestMethod.GET)
    public String getCompany(
            @PathVariable UUID id,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        CompanyDto company = companyService.getCompany(id, token);
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("company",company);
        return "/apartment/companyDetails";
    }
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public String getCompanyByOwner(
            @PathVariable UUID id,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        model.addAttribute("companies",companyService.getCompanyByOwner(id, token));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("user",userService.getUserById(id).getDetails());
        return "/apartment/companies";
    }
}
