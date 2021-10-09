package com.contoso.demoweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {
 
    private static final String HOME_INDEX ="welcome"; 
    private static final String VIEW_DASHBOARD ="dashboard"; 


    @GetMapping("/")
    public String index(Model model) {
        return HOME_INDEX;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return VIEW_DASHBOARD;
    }
}
