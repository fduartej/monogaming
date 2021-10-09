package com.contoso.demoweb.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.*;

import com.contoso.demoweb.repository.*;

@RestController
@RequestMapping(value = "api/dashboard", produces = "application/json")
public class DashboardController {

    private final DetallePedidoRepository pedidosData;

    public DashboardController(DetallePedidoRepository pedidosData){
        this.pedidosData = pedidosData;
    } 

    @GetMapping(value = "/pedidostotales", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> productos(){
        return  new ResponseEntity<List<Map<String, Object>>>(
            pedidosData.querySumaTotal(), HttpStatus.OK);
    }


}