package uz.pdp.cityfront.controller.booking;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.pdp.cityfront.domain.dto.booking.BookFlatDto;
import uz.pdp.cityfront.domain.dto.user.UserReadDto;
import uz.pdp.cityfront.service.apartment.FlatService;
import uz.pdp.cityfront.service.booking.BookingService;
import uz.pdp.cityfront.service.user.UserService;
import uz.pdp.cityfront.util.Utils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    private final FlatService flatService;

    @RequestMapping(value = "/flat",method = RequestMethod.POST)
    public String bookFlat(
            HttpServletResponse response,
            HttpServletRequest request,
            BookFlatDto bookFlatDto,
            Model model
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("flat",flatService.getFlat(bookFlatDto.getFlatId(),token));
        String message = bookingService.book(bookFlatDto,token);
        model.addAttribute("message",message == null ? "Error occurred" : message);
        return "apartment/flatDetails";
    }
    @RequestMapping(value = "/flat/buy",method = RequestMethod.POST)
    public String buyFlat(
            HttpServletResponse response,
            HttpServletRequest request,
            Model model,
            BookFlatDto bookFlatDto
    ) {
        String token = Utils.getCookie("token", request);
        String email = Utils.getCookie("email", request);
        response.addCookie(Utils.createCookie("token",token));
        response.addCookie(Utils.createCookie("email",email));
        UserReadDto user = userService.getUserByUsername(email);
        model.addAttribute("user",user);
        model.addAttribute("role",userService.getRole(user));
        model.addAttribute("flat",flatService.getFlat(bookFlatDto.getFlatId(), token));
        String message = bookingService.buyFlat(bookFlatDto,token);
        model.addAttribute("message", message == null ? "Error occurred" : message);
        return "apartment/flatDetails";
    }
}
