package gob.sunat.apisunat.service;

import org.springframework.stereotype.Service;
import gob.sunat.apisunat.model.*;

import java.util.*;

@Service
public class GeneracionFactura {

    public void generarNumeroFactura(Invoice invoice){
        //GRABAR EN LA DB SUNAT
        //ACTIVAR LAS DECLARACIONES
        invoice.setNumeroFactura(UUID.randomUUID().toString());
        invoice.setStatus("PROCESADO");
    }
    
}
