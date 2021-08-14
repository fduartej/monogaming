package com.acme.demoapi.controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.*;


@RestController
@RequestMapping(value ="api/acme", produces ="application/json")
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saludo(@RequestParam String nombre){
        return  new ResponseEntity<String>(
            "Saludo " + nombre, HttpStatus.OK);
    }


}
