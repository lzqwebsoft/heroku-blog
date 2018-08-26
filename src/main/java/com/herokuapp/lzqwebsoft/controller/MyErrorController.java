package com.herokuapp.lzqwebsoft.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
;import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return "error404";
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value() || statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            return "error503";
        } else {
            return "error";
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
