package uz.pdp.cityfront.controller.apartment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.service.apartment.AccommodationService;
import uz.pdp.cityfront.service.apartment.FlatService;
import uz.pdp.cityfront.util.Utils;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/apartment")
public class AccommodationController {
    private final AccommodationService accommodationService;
    private final FlatService flatService;

    @RequestMapping(path = "/search",method = RequestMethod.POST)
    public String search(
            Filter filter,
            Model model,
            HttpServletRequest request
    ){
        String accessToken = Utils.getCookie("token", request);
        model.addAttribute("accommodations",accommodationService.search(filter,accessToken));
        return "/apartment/accommodations";
    }
    @RequestMapping(path = "/get/acc/{accId}",method = RequestMethod.GET)
    public String getAcc(
            Model model,
            HttpServletRequest request,
            @PathVariable UUID accId
    ){
        String accessToken = Utils.getCookie("token", request);
        model.addAttribute( "accommodation",accommodationService.getAcc(accId,accessToken));
        model.addAttribute("flats",flatService.getFlats(accId,accessToken));
        model.addAttribute( "block",false);
        return "/apartment/accDetails";
    }

}
