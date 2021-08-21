package com.acme.demoapi.model;

import java.util.Date;
import java.math.BigDecimal;
import lombok.*;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numeroFactura;
    private String codigoCliente;
    private String nombreCliente;
    private String direccion;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaFactura;
    private BigDecimal totalFactura;
    @Transient
    private List<DetalleFactura> detalleFacturas;
    
}
