package com.herokuapp.lzqwebsoft.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyErrorController extends BaseController implements ErrorController {

    @RequestMapping("/error")
    public void handleError(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        model.addAttribute("errorCode", statusCode);
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            render("error404", model, request, response);
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value() || statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            render("error503", model, request, response);
        } else {
            render("errorNO", model, request, response);
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
