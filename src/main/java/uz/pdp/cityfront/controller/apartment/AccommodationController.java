package uz.pdp.cityfront.controller.apartment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.service.apartment.AccommodationService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/apartment")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @RequestMapping(path = "/search",method = RequestMethod.GET)
    public String search(
            Filter filter,
            @RequestParam UUID userId,
            Model model
    ){
        model.addAttribute("accommodations",accommodationService.search(filter,userId));
        return "/apartment/accommodations";
    }
}
