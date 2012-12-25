package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
	
	@RequestMapping("/images/upload")
	public void upload(@RequestParam("imgFile")MultipartFile file,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json;charset=UTF-8");
			StringBuffer json = new StringBuffer();
			json.append("{\"error\": 0, \"url\": \"/heroku-blog/resources/images/header.jpg\"}");
			
			out.print(json);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			if(out!=null) {
				out.close();
			}
		}
	}
	
	@RequestMapping("/images/list")
	public void list(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json;charset=UTF-8");
	}
}
