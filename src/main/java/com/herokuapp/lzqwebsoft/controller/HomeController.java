package com.herokuapp.lzqwebsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController{
	
	@RequestMapping(value="/index")
	public String home() {
		return "index";
	}
	
	@RequestMapping(value="/show/{articleId}")
	public String show(@PathVariable("articleId")String articleId) {
	    return "show";
	}
	
	@RequestMapping(value="/new") 
	public String newArticle(){
	    return "new";
	}
	
	@RequestMapping(value="/set")
	public String set() {
		return "set";
	}
	
	@RequestMapping(value="/change_password")
    public String changePassword(){
        return "change_password";
    }
}