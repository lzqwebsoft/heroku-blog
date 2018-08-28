package com.herokuapp.lzqwebsoft.controller;

import com.google.gson.Gson;
import io.undertow.servlet.spec.HttpServletResponseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

    public static int ERROR = 1;
    public static int SUCCESS = 0;

    @Autowired
    private ViewResolver viewResover;

    // 成功的JSON
    public String successJSON(String message) {
        return generateJson(SUCCESS, message, null);
    }

    public String successJSON(String message, Map<String, Object> datas) {
        return generateJson(SUCCESS, message, datas);
    }

    // 失败的JSON
    public String errorJSON(String message) {
        return this.generateJson(ERROR, message, null);
    }

    public String errorJSON(List<String> messages) {
        return generateJson(ERROR, messages, null);
    }

    private String generateJson(int status, Object message, Map<String, Object> datas) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("status", status);
        json.put("messages", message);
        json.put("datas", datas);
        Gson gson = new Gson();
        return gson.toJson(json);
    }

    public String render(String view, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        View resolvedView = null;
        try {
            resolvedView = viewResover.resolveViewName(view, request.getLocale());
            resolvedView.render(model, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
