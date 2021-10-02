package com.contoso.demoweb.controller;

import javax.validation.Valid;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.contoso.demoweb.model.*;
import com.contoso.demoweb.repository.UsuarioRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UsuarioController {
 
    private static final String INDEX ="usuario/login"; 
    private static String MODEL_CONTACT="user";
    private static String MODEL_MESSAGE="mensaje";
    private final UsuarioRepository usuariosData;

    public UsuarioController(UsuarioRepository usuariosData){
        this.usuariosData = usuariosData;
    }      


    @GetMapping("/usuario/login")
    public String login(Model model) {
        model.addAttribute("user", new Usuario());
        return INDEX;
    }  

    @PostMapping("/usuario/login")
    public String loginSubmitForm(Model model, 
         @Valid Usuario objUser, 
         HttpServletRequest request,
         BindingResult result ){
            String page=INDEX;
            model.addAttribute(MODEL_CONTACT, new Usuario());
            if(result.hasFieldErrors()) {
                model.addAttribute(MODEL_MESSAGE, "No se ha podido loguear");
            }else{
                Optional<Usuario> userDB = this.usuariosData.findById(objUser.getUserID());
                if(userDB.isPresent()){
                    if(userDB.get().getPassword().equals(objUser.getPassword())){
                        model.addAttribute(MODEL_CONTACT,userDB.get());
                        model.addAttribute(MODEL_MESSAGE, "Usuario existe");
                        request.getSession().setAttribute("user", objUser);
                        page="welcome";  
                    }else{
                        model.addAttribute(MODEL_MESSAGE, "Password no coincide");  
                    }
                }else{
                    model.addAttribute(MODEL_MESSAGE, "Usuario no existe");
                }
            }
            return page;             
    }

    @GetMapping("/usuario/logout")
	public String logoutSession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
}
