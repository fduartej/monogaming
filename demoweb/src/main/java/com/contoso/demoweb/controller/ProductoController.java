package com.contoso.demoweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import com.contoso.demoweb.model.Producto;
import com.contoso.demoweb.repository.ProductoRepository;

import javax.validation.Valid;

import java.util.List;

@Controller
public class ProductoController {
 
    private static final String VIEW_INDEX ="producto/index";
    private static final String VIEW_CREATE="producto/create"; 
    private static final String VIEW_EDIT="producto/edit"; 
    private static String MODEL_PRODUCTO="producto";
    private final ProductoRepository productsData;
    

    public ProductoController(ProductoRepository productsData
        ){
        this.productsData = productsData;
        
    }      

    @GetMapping("/producto/index")
    public String index(Model model){
        List<Producto> listProducto = this.productsData.findAll();
        model.addAttribute("productos",listProducto);
        return VIEW_INDEX;
    }    

    @GetMapping("/producto/create")
    public String create(Model model) {
        model.addAttribute(MODEL_PRODUCTO, new Producto());
        return VIEW_CREATE;
    }    

    @PostMapping("/producto/create")
    public String createSubmitForm(Model model, 
        @Valid Producto objProducto, BindingResult result ){
        if(result.hasFieldErrors()) {
            model.addAttribute("mensaje", "No se registro un Producto");
        }else{
            objProducto.setStatus("A");
            this.productsData.save(objProducto);
            model.addAttribute(MODEL_PRODUCTO, objProducto);
            model.addAttribute("mensaje", "Se registro un Producto");
        }
        return VIEW_CREATE;
    }

    @GetMapping("/producto/edit/{id}")
    public String edit(@PathVariable("id") Integer id, 
        Model model){
        Producto producto = this.productsData.getById(id);
        model.addAttribute(MODEL_PRODUCTO, producto);
        return VIEW_EDIT;
    }  

    @PostMapping("/producto/edit")
	public String update(
			@Valid Producto objProducto,
			BindingResult bindingResult
			){
		if(bindingResult.hasFieldErrors()) {
			return "redirect:/producto/edit/{id}";
		}
		this.productsData.save(objProducto);
		return VIEW_INDEX;
	}

}
