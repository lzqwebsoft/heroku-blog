package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.herokuapp.lzqwebsoft.pojo.Image;
import com.herokuapp.lzqwebsoft.service.ImageService;

@Controller
public class ImageController {
    
    @Autowired
    private ImageService imageService;
	
	@RequestMapping("/images/upload")
	public void upload(@RequestParam("imgFile")MultipartFile file, String imgTitle,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json;charset=UTF-8");
			StringBuffer json = new StringBuffer();
			
			Image image = new Image();
			image.setDescriptions(imgTitle);
			String filePath = imageService.save(file, image);
			if(filePath==null) {
			    json.append("{\"error\": 1}");
			} else {
			    json.append("{\"error\": 0, \"url\": \""+request.getContextPath()+"/upload-images/"+ filePath +"\"}");
			}
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
	public void list(HttpServletRequest request, HttpServletResponse response) {
	    response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        
	    PrintWriter out = null;
        try {
            out = response.getWriter();
            StringBuffer json = new StringBuffer("{\"file_list\":[");
            
            List<Image> images = imageService.getAllImages();
            for(Image image : images) {
                json.append("{\"filename\": \"").append(image.getDiskFilename())
                .append("\", \"filesize\": \"").append(image.getSize())
                .append("\", \"current_url\": \"").append(request.getContextPath()).append("/upload-images/")
                .append("\", \"datetime\": \"").append(image.getCreateAt()).append("\"}, ");
            }
            json.deleteCharAt(json.length()-1);
            json.append("]}");
            out.print(json);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            if(out!=null) {
                out.close();
            }
        }
	}
}
