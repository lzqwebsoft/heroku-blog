package com.herokuapp.lzqwebsoft.controller;

import com.google.gson.Gson;
import com.herokuapp.lzqwebsoft.util.HtmlSingleLineWriter;
import com.herokuapp.lzqwebsoft.util.IP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.IntStream;

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

    public String errorJSON(String message, Map<String, Object> datas) {
        return generateJson(ERROR, message, datas);
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
            response = HtmlSingleLineWriter.wrapResponse(response);
            resolvedView = viewResover.resolveViewName(view, request.getLocale());
            resolvedView.render(model, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获取IP地址
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) || ip.split("\\.").length != 4) {
            return "0.0.0.0";
        }
        return ip;
    }

    // 获取IP地址归属地
    public String getIPLocation(String ip) {
        if (ip == null || ip.trim().length() == 0)
            return "";
        try {
            String[] resultIP = IP.find(ip);
            if (resultIP != null && resultIP.length > 0) {
                Set<String> set = new HashSet<String>(Arrays.asList(resultIP));
                StringBuffer location = new StringBuffer();
                for (String place : set) {
                    location.append(place);
                }
                return location.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
