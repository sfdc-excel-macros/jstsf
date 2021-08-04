package com.lycos.freemarkerdemo;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

@Controller
public class SimpleController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "happy birthday");
        return "welcome"; //FP, static home page
    }

    // http://127.0.0.1:8090/freeview?taint=admin/admin
    @GetMapping("/freeview")
    public String freeview(Model model, @RequestParam String taint) {

        model.addAttribute("message", "happy birthday");
        return taint;
    }

    // http://127.0.0.1:8090/path?lang=en/../../admin
    @GetMapping("/path")
    public String path(@RequestParam String lang) {
        return "user/" + lang + "/welcome";
    }

    // http://127.0.0.1:8090/model?section=admin/welcome/../../user/en/welcome
    @GetMapping("/model")
    public Object model(@RequestParam String section) {

        return new ModelAndView(section);
    }

    // http://127.0.0.1:8090/model/other?action=3&section=admin/welcome/../../user/en/welcome
    @GetMapping("/model/other") //other ModelAndView sinks
    public ModelAndView modelOther(@RequestParam String section, @RequestParam int action, Model m) {
        String viewName = section;
        switch (action) {
            case 1:
                ModelAndView modelAndView1 = new ModelAndView(viewName, HttpStatus.OK);
                return modelAndView1;

            case 2:
                ModelAndView modelAndView2 = new ModelAndView(viewName, m.asMap());
                return modelAndView2;

            default:
                ModelAndView modelAndView3 = new ModelAndView();
                modelAndView3.setViewName(viewName);
                return modelAndView3;
        }
    }

    @GetMapping("/redirect")
    public String redirect(@RequestParam String url) {
        return "redirect:" + url;
    }

    // http://127.0.0.1:8090/ajaxredirect?name=../../../../user/en/welcome
    @GetMapping("/ajaxredirect")
    public String ajaxredirect(@RequestParam String name) {
        return "ajaxredirect:" + name;
    }

    @GetMapping("/_safe/redirect")
    public String safeRedirect(@RequestParam String url) {
        return "redirect:./login?xxx=" + url; //FP, as we can control the hostname in redirect
    }

    @GetMapping("/model/pathdefault/{id}")
    public Object modelDefaultPathVariable(@PathVariable("id") String id) {
        return new Object(); // FP
    }

    @GetMapping("/model/default*")
    public Object modelDefaultStar() {
        return new Object(); // FP
    }

}
