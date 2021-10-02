package com.contoso.demoweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

import javax.validation.Valid;

import com.contoso.demoweb.model.Contacto;
import com.contoso.demoweb.repository.ContactoRepository;

@Controller

public class ContactoController {
    private static final String INDEX ="contacto/create"; 
    private static String MODEL_CONTACT="contact";
    private final ContactoRepository contactsData;

    public ContactoController(ContactoRepository contactsData){
        this.contactsData = contactsData;
    }    

    @GetMapping("/contacto/create")
    public String index(Model model) {
        model.addAttribute(MODEL_CONTACT, new Contacto());
        return INDEX;
    }  
    
    @PostMapping("/contacto/create")
    public String createSubmitForm(Model model, 
        @Valid Contacto objContact, BindingResult result ){
        if(result.hasFieldErrors()) {
            model.addAttribute("mensaje", "No se registro un contacto");
        }else{
            this.contactsData.save(objContact);
            model.addAttribute(MODEL_CONTACT, objContact);
            model.addAttribute("mensaje", "Se registro un contacto");
        }
        return INDEX;
    } 
}
