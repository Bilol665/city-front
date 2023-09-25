package uz.pdp.cityfront.controller.apartment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.pdp.cityfront.domain.dto.filter.Filter;
import uz.pdp.cityfront.service.apartment.AccommodationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/apartment")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @RequestMapping(path = "/search",method = RequestMethod.GET)
    public String search(
            @ModelAttribute Filter filter,
            Model model
    ){
//        model.addAttribute("accommodations",accommodationService.search(filter));
        return "/apartment/accommodations";
    }
}
