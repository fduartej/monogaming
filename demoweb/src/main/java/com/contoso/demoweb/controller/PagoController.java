package com.contoso.demoweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import com.contoso.demoweb.model.Cliente;
import com.contoso.demoweb.model.DetallePedido;
import com.contoso.demoweb.model.Pago;
import com.contoso.demoweb.model.Pedido;
import com.contoso.demoweb.model.Proforma;
import com.contoso.demoweb.model.Usuario;
import com.contoso.demoweb.repository.ClienteRepository;
import com.contoso.demoweb.repository.DetallePedidoRepository;
import com.contoso.demoweb.repository.PagoRepository;
import com.contoso.demoweb.repository.PedidoRepository;
import com.contoso.demoweb.repository.ProformaRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class PagoController {

    private static final String VIEW_INDEX ="pago/create";
    private static String MODEL_VIEW="pago";
    private final ProformaRepository proformaData;
    private final ClienteRepository clienteData;
    private final PagoRepository pagoData;
    private final PedidoRepository pedidoData;
    private final DetallePedidoRepository detallePedidoData;
    
    public PagoController(ProformaRepository proformaData,
        ClienteRepository clienteData,
        PagoRepository pagoData,
        PedidoRepository pedidoData,
        DetallePedidoRepository detallePedidoData
        ){
        this.proformaData = proformaData;
        this.clienteData = clienteData;
        this.pagoData = pagoData;
        this.pedidoData = pedidoData;
        this.detallePedidoData = detallePedidoData;
    } 
    
    
    @GetMapping("/pago/create")
    public String index(Model model, HttpSession session){
        Usuario user = (Usuario)session.getAttribute("user"); 
        Cliente cliente = clienteData.findByUsuario(user);
        List<Proforma> listItems = proformaData.findItemsByUsuario(user);
        if(listItems.isEmpty()){
            model.addAttribute("mensaje", "Carrito Vacio");
            return "proforma/index";
        }
        BigDecimal montoTotal = listItems.stream().
            map(n -> n.getPrecio().multiply(
                        new BigDecimal(n.getCantidad()))).
                    reduce(BigDecimal.ZERO, BigDecimal::add);
        Pago pago = new Pago();
        pago.setPaymentDate(new Date());
        pago.setMontoTotal(montoTotal);
        pago.setClienteId(cliente.getId());
        pago.setNombreTarjeta(
            cliente.getFirstName().concat(" ").
            concat(cliente.getLastName()));
        model.addAttribute(MODEL_VIEW, pago);
        return VIEW_INDEX;
    }   

    @PostMapping("/pago/create")
    public String createSubmitForm(Model model, HttpSession session,
        @Valid Pago objPago, BindingResult result ){
        Usuario user = (Usuario)session.getAttribute("user"); 
        if(result.hasFieldErrors()) {
            model.addAttribute("mensaje", "No se puede registrar pago");
        }else{
            Pedido pedido = new Pedido();
            pedido.setClienteId(objPago.getClienteId());
            pedido.setMontoTotal(objPago.getMontoTotal());
            pedido.setOrderDate(new Date());
            pedidoData.save(pedido);
            List<Proforma> listItems = proformaData.findItemsByUsuario(user);
            listItems.stream().forEach(o -> o.setStatus("PROCESED"));
            proformaData.saveAll(listItems);
            List<DetallePedido> detalles = new ArrayList<DetallePedido>();
            for (Proforma pro : listItems) {
                DetallePedido dp = new DetallePedido();
                dp.setPedido(pedido);
                dp.setProduct(pro.getProduct());
                dp.setPrecio(pro.getPrecio());
                dp.setCantidad(pro.getCantidad());
                detalles.add(dp);
            }
            detallePedidoData.saveAll(detalles);
            objPago.setPaymentDate(new Date());
            pagoData.save(objPago);
            model.addAttribute(MODEL_VIEW, objPago);
            pedidoData.flush();
            model.addAttribute("mensaje", "Se registro su pago y se genero su pedido nro "+ pedido.getId());
        }
        return VIEW_INDEX;
    }
}
