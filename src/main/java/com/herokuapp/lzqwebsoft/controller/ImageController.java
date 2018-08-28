package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.herokuapp.lzqwebsoft.pojo.Image;
import com.herokuapp.lzqwebsoft.pojo.Page;
import com.herokuapp.lzqwebsoft.service.ImageService;

@Controller
public class ImageController extends BaseController {

    @Autowired
    private ImageService imageService;

    @ResponseBody
    @RequestMapping("/images/upload.html")
    public Map<String, String> upload(@RequestParam("imgFile") MultipartFile file, String imgTitle, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        Image image = new Image();
        image.setDescriptions(imgTitle);
        String image_url = imageService.save(file, image);

        Map<String, String> json = new HashMap<>();
        if (image_url == null) {
            json.put("error", "1");
        } else {
            json.put("error", "0");
            json.put("url", request.getContextPath() + "/" + image_url);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping("/images/list.html")
    public Map<String, Object> list(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> json = new HashMap<>();
        List<Map<String, String>> fileList = new ArrayList<>();

        List<Image> images = imageService.getAllImages();
        if (images != null && images.size() > 0) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Image image : images) {
                Map<String, String> imageJSON = new HashMap<>();
                StringBuffer image_url = new StringBuffer(image.getId()).append(".jpg");
                imageJSON.put("filename", image_url.toString());
                imageJSON.put("is_photo", "true");
                imageJSON.put("is_dir", "false");
                imageJSON.put("filesize", String.valueOf(image.getSize()));
                imageJSON.put("datetime", format.format(image.getCreateAt()));
                fileList.add(imageJSON);
            }
            json.put("current_url", request.getContextPath() + "/images/show/");
            json.put("total_count", String.valueOf(images.size()));
        } else {
            json.put("total_count", 0);
        }
        json.put("file_list", fileList);
        return json;
    }

    @RequestMapping("/images/delete/{imageId}.jpg")
    public String delete(@PathVariable("imageId") String id, int pageNo, ModelMap model) {
        imageService.delete(id);
        if (pageNo <= 0)
            pageNo = 1;
        Page<Image> images = imageService.getAllImagesByPage(pageNo, 20);
        if ((images.getData() == null || images.getData().size() <= 0) && pageNo > 1) {
            model.addAttribute("pageNo", pageNo - 1);
            return "redirect:/images/page.html";
        }
        model.addAttribute("images", images);
        return "_images_tab";
    }

    @RequestMapping("/images/page.html")
    public String select(Integer pageNo, HttpServletRequest request) {
        if (pageNo == null || pageNo <= 0)
            pageNo = 1;

        Page<Image> images = imageService.getAllImagesByPage(pageNo, 20);
        request.setAttribute("images", images);

        return "_images_tab";
    }

    @ResponseBody
    @RequestMapping("/images/show/{imageId}.jpg")
    public byte[] show(@PathVariable("imageId") String id, HttpServletResponse response) {
        Image image = imageService.getImageById(id);

        if (image != null) {
            // 显示动态图片
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(image.getContentType());
            response.setContentLengthLong(image.getSize());

            return image.getContent();
        } else {
            try {
                response.sendRedirect("/resources/images/NoImageShow.jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
