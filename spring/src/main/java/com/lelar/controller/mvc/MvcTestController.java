package com.lelar.controller.mvc;

import com.lelar.controller.rest.AuthController;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;
//import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
//@SessionAttributes("testAttribute")
@RequestMapping("/mvc")
//@AllArgsConstructor
public class MvcTestController {
//    private final AuthController controller;

//    @ModelAttribute
//    public void fillTest(Model model) {
//        model.addAttribute("first", "ATTRIBUTE");
//    }
//
//    @ModelAttribute
//    public DataClass fillDataClass() {
//        DataClass attributeValue = new DataClass();
//        attributeValue.setField("DATA!!!");
//        return attributeValue;
//    }
//

    @GetMapping(path = "/get")
    public String getTest(Model book) {
        book.addAttribute("books", Set.of(new Book("isbn1", "name1", "author1"), new Book("isbn2", "name2", "author2")));
        return "index";
    }

    @GetMapping(path = "/login")
    public String getModel(ModelAndView modelAndView2) {
        System.out.println(modelAndView2);
//        modelAndView2.getModel()
//        controller.login()

//        System.out.println(modelAndView2);
//        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView2.setViewName("login");
//        modelAndView2.addObject("books", Set.of(new Book("isbn1", "name1", "authorasdgfasga"), new Book("isbn2", "name2", "author2")));
        return "login";
    }

}

