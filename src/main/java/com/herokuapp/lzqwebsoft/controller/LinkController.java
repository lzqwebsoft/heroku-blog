package com.herokuapp.lzqwebsoft.controller;

import com.herokuapp.lzqwebsoft.pojo.Link;
import com.herokuapp.lzqwebsoft.pojo.Page;
import com.herokuapp.lzqwebsoft.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/link")
public class LinkController extends BaseController {

    private int pageSize = 15;

    @Autowired
    private LinkService linkService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/page.html")
    public String page(Integer pageNo, ModelMap model) {
        if (pageNo == null || pageNo <= 0)
            pageNo = 1;

        Page<Link> links = linkService.getAllLinksByPage(pageNo, pageSize);
        model.addAttribute("links", links);
        model.addAttribute("link", new Link());

        return "_links_tab";
    }

    @RequestMapping(value = "/delete/{linkId}.html")
    public String delete(@PathVariable("linkId") Integer linkId, int pageNo, ModelMap model) {
        linkService.delete(linkId);
        if (pageNo <= 0)
            pageNo = 1;
        Page<Link> links = linkService.getAllLinksByPage(pageNo, pageSize);
        if ((links.getData() == null || links.getData().size() <= 0) && pageNo > 1) {
            model.addAttribute("pageNo", links.getTotalPageCount());
            return "redirect:/link/page.html";
        }
        model.addAttribute("links", links);
        model.addAttribute("link", new Link());
        return "_links_tab";
    }

    @ResponseBody
    @RequestMapping(value = "/get/{id}.html")
    public String get(@PathVariable("id") Integer id, HttpServletResponse response, Locale locale) {
        Link link = linkService.getLinkById(id);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        String successMessage = messageSource.getMessage("info.fetch.success", null, locale);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("link", link);
        return successJSON(successMessage, result);
    }

    @ResponseBody
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public String save(Link link, HttpServletResponse response, Locale locale) {
        String nameLabel = messageSource.getMessage("page.label.linkname", null, locale);
        String remarkLabel = messageSource.getMessage("page.label.remark", null, locale);

        List<String> errors = new ArrayList<String>();
        String path = link.getPath();
        if (path != null && path.trim().length() > 0 && !path.matches("^((http[s]?)(://))(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*$")) {
            errors.add(messageSource.getMessage("info.invalid.website", null, locale));
        }
        String name = link.getName();
        if (name == null || name.trim().length() <= 0) {
            errors.add(messageSource.getMessage("info.required", new Object[]{nameLabel}, locale));
        }
        String remark = link.getRemark();
        if (remark != null && remark.trim().length() > 200) {
            errors.add(messageSource.getMessage("info.length.long", new Object[]{remarkLabel, 200}, locale));
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        if (errors.size() > 0) {
            return errorJSON(errors);
        } else {
            linkService.save(link);
            String successMessage = messageSource.getMessage("info.links.save.success", null, locale);
            return successJSON(successMessage);
        }
    }
}
