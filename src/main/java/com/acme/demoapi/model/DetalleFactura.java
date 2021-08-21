package com.acme.demoapi.model;

import javax.persistence.*;
import java.math.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_detalle_factura")
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int cantidad;
    private BigDecimal precio;
    private String producto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")    
    private Factura factura;
}
