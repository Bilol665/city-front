package uz.pdp.cityfront.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

public class Utils {
    public static String getCookie(String name, HttpServletRequest request) {
        for (Cookie cookie: request.getCookies()) {
            if(Objects.equals(cookie.getName(),name))
                return cookie.getValue();
        }
        return "null";
    }
    public static Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        return cookie;
    }
    public static void checkHttpStatus(String message) {
        message = message.substring(0,3);
        switch (message) {
            case "403" -> {

            }
        }
    }
    public static void removeCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                removeCookie(response, cookie.getName());
            }
        }
    }
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
