package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
			String image_url = imageService.save(file, image);
			if(image_url==null) {
			    json.append("{\"error\": 1}");
			} else {
			    json.append("{\"error\": 0, \"url\": \""+request.getContextPath()+"/"+ image_url +"\"}");
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
            if(images!=null&&images.size()>0) {
            	for(Image image : images) {
            	    StringBuffer image_url = new StringBuffer(request.getContextPath()).append("/images/show/").append(image.getId()).append(".html");
                    json.append("{\"filename\": \"").append(image.getFileName())
                        .append("\", \"dir_path\": \"").append(image_url.toString())
                        .append("\", \"is_photo\": true")
                        .append(", \"is_dir\": false") 
                        .append(", \"filesize\": \"").append(image.getSize())
                        .append("\", \"datetime\": \"").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(image.getCreateAt())).append("\"}, ");
                }
                json.deleteCharAt(json.length()-1);
                json.append("], ");
                json.append("\"current_url\": \"").append(request.getContextPath()).append("/upload-images/")
                    .append("\", \"total_count\": ").append(images.size()).append("}");
            } else {
            	json.append("], \"total_count\": 0}");
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
	
	@RequestMapping("/images/delete/{imageId}")
	public String delete(@PathVariable("imageId")String id, ModelMap model) {
	    imageService.delete(id);
	    
	    List<Image> images = imageService.getAllImages();
	    model.addAttribute("images", images);
	    return "_images_tab";
	}
	
	@RequestMapping("/images/show/{imageId}.html")
	public void show(@PathVariable("imageId")String id, HttpServletResponse response) {
	    Image image = imageService.getImageById(id);
	    
	    if(image!=null) {
	        // 显示动态图片
	        response.setStatus(HttpServletResponse.SC_OK);
	        response.setContentType(image.getContentType());
	        response.setContentLength((int)image.getSize());
	        ServletOutputStream out = null;
	        try {
	            out = response.getOutputStream();
	            out.write(image.getContent());
	        } catch(IOException e) {
	            e.printStackTrace();
	            if(out!=null) {
	                try {
                        out.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
	            }
	        }
	    }
	    else {
	        try {
                response.sendRedirect("resources/images/NoImageShow.jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
	    }
	}
}
