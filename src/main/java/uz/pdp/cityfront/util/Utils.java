package uz.pdp.cityfront.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

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
}
