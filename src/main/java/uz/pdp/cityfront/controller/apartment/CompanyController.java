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
import uz.pdp.cityfront.domain.dto.apartment.CompanyCreateDto;
import uz.pdp.cityfront.domain.dto.apartment.CompanyDto;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.service.apartment.CompanyService;
import uz.pdp.cityfront.service.payment.PaymentService;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

import java.util.UUID;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final UserService userService;
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;
    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public String addNewCompany(
            CompanyCreateDto companyCreateDto,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        companyService.addNewCompany(companyCreateDto,token);
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("companies",companyService.getCompanyByOwner(user.getId(),token));
        model.addAttribute("replace",true);
        try {
            model.addAttribute("userJSON",objectMapper.writeValueAsString(user));
            model.addAttribute("cards",new ObjectMapper().writeValueAsString(paymentService.getCardUser(user.getId(),token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "apartment/companies";
    }
    @RequestMapping(path = "/getPage/add",method = RequestMethod.GET)
    public String getAddCompanyPage(
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        return "apartment/addCompany";
    }
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
        try {
            model.addAttribute("userJSON",objectMapper.writeValueAsString(user));
            model.addAttribute("cards",objectMapper.writeValueAsString(paymentService.getCardUser(user.getId(),token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "/apartment/companies";
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
        model.addAttribute("companies",companyService.getAllCompanies(new Filter(),token));
        try {
            model.addAttribute("userJSON",objectMapper.writeValueAsString(user));
            model.addAttribute("cards",objectMapper.writeValueAsString(paymentService.getCardUser(user.getId(),token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        return "/apartment/companies";
    }
}
