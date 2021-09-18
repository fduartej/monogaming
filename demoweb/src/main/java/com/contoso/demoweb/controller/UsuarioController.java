package com.contoso.demoweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.contoso.demoweb.model.*;

@Controller
public class UsuarioController {
 
    private static final String INDEX ="usuario/login"; 

    @GetMapping("/usuario/login")
    public String login(Model model) {
        model.addAttribute("user", new Usuario());
        return INDEX;
    }  
}
