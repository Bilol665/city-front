package uz.pdp.cityfront.controller.apartment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityfront.domain.dto.apartment.ReadFromJs;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.service.apartment.AccommodationService;
import uz.pdp.cityfront.service.apartment.FlatService;
import uz.pdp.cityfront.util.Utils;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/flat")
public class FlatController {
    private final FlatService flatService;
    private final AccommodationService accommodationService;

    @RequestMapping(value = "/acc/{accId}",method = RequestMethod.GET)
    public String getAccommodations(
            @PathVariable UUID accId,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        model.addAttribute("flats",flatService.getFlats(accId,token));
        model.addAttribute("accommodation",accommodationService.getAcc(accId,token));
        response.addCookie(Utils.createCookie("token",token));
        return "/apartment/flats";
    }
    @RequestMapping(value = "/search/{accId}",method = RequestMethod.POST)
    public String search(
            @RequestBody ReadFromJs readFromJs,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            @PathVariable UUID accId
    ) {
        String token = Utils.getCookie("token", request);
        model.addAttribute("flats",flatService.search(readFromJs,token));
        model.addAttribute("accommodation",accommodationService.getAcc(accId,token));
        response.addCookie(Utils.createCookie("token",token));
        return "/apartment/flats";
    }
}
