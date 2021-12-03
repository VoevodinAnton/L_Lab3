package com.example.l_lab3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("operation", new SumOperation());
        return "index";
    }

    @PostMapping("/")
    public String doOperation(@ModelAttribute SumOperation sumOperation, Model model){
        model.addAttribute("result", sumOperation.getA() + sumOperation.getB());
        return "result";
    }
}
