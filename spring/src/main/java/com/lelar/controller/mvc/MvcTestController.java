package com.lelar.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
//@SessionAttributes("testAttribute")
@RequestMapping("/mvc")
public class MvcTestController {

    @ModelAttribute
    public void fillTest(Model model) {
        model.addAttribute("first", "ATTRIBUTE");
    }

    @ModelAttribute
    public DataClass fillDataClass() {
        DataClass attributeValue = new DataClass();
        attributeValue.setField("DATA!!!");
        return attributeValue;
    }

    @GetMapping(path = "/get")
    public String getTest() {
        System.out.println("dsagag");
        return "test";
    }

}
